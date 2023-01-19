package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PG JDBC Driver?");
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            deleteSections(conn, r);
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });

    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
                readContact(conn, r);
                readSection(conn, r);
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute(
                "DELETE FROM resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try(PreparedStatement psMain = conn.prepareStatement("SELECT * FROM resume r ORDER BY r.full_name, r.uuid")) {
               ResultSet rsMain = psMain.executeQuery();
               Resume r;
               while (rsMain.next()) {
                   String uuid = rsMain.getString("uuid");
                   r = new Resume(uuid, rsMain.getString("full_name"));
                   readContact(conn, r);
                   readSection(conn, r);
                   map.put(uuid, r);
               }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        LOG.info("counting size");
        return sqlHelper.execute(
                "SELECT count(*) AS COUNT FROM resume r",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new StorageException("error while counting resume rows");
                    }
                    return rs.getInt("COUNT");
                }
        );
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                String sectionType = e.getKey().name();
                ps.setString(2, sectionType);

                if (sectionType.equals("PERSONAL") || sectionType.equals("OBJECTIVE")) {
                    TextSection textSection = (TextSection) e.getValue();
                    ps.setString(3, textSection.getContent());
                } else if (sectionType.equals("ACHIEVEMENT") || sectionType.equals("QUALIFICATIONS") ) {
                    ListSection listSection = (ListSection) e.getValue();
                    ps.setString(3, String.join("\n", listSection.getItems()));
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.executeUpdate();
        }
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.executeUpdate();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        if (rs.getString("resume_uuid") != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
        }
    }

    private void readContact(Connection conn, Resume r) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement(
                "SELECT c.*" +
                        "    FROM contact c" +
                        "    WHERE c.resume_uuid =?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
        }
    }

    private void readSection(Connection conn, Resume r) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement(
                "SELECT s.*" +
                        "   FROM section s" +
                        "   WHERE s.resume_uuid =?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String sectionType = rs.getString("type");
                String sectionValue = rs.getString("value");
                if ("OBJECTIVE".equals(sectionType) || "PERSONAL".equals(sectionType)) {
                    TextSection textSection = new TextSection(sectionValue);
                    r.addSection(
                            SectionType.valueOf(sectionType),
                            textSection
                    );
                } else if ("ACHIEVEMENT".equals(sectionType) || "QUALIFICATIONS".equals(sectionType)) {
                    r.addSection(
                            SectionType.valueOf(sectionType),
                            new ListSection(Arrays.asList(sectionValue.split("\n")))
                    );
                }
            }
        }
    }
}

