package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }


    private void clearResumeContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=? AND type=?")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.executeUpdate();
            }
        }
    }

    private void fillResumeContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.executeUpdate();
            }
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
                ps.setString(2, r.getUuid());
                ps.setString(1, r.getFullName());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }

            clearResumeContacts(conn, r);
            fillResumeContacts(conn, r);
//            try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value=? WHERE resume_uuid=? AND type=?")) {
//                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
//                    ps.setString(1, e.getValue());
//                    ps.setString(2, r.getUuid());
//                    ps.setString(3, e.getKey().name());
//
//                    if (ps.executeUpdate() == 0) {
//                        try (PreparedStatement psUpd = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
//                            psUpd.setString(1, r.getUuid());
//                            psUpd.setString(2, e.getKey().name());
//                            psUpd.setString(3, e.getValue());
//                            psUpd.execute();
//                        }
//                    }
//                }
//            }
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
            fillResumeContacts(conn, r);
//            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
//                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
//                    ps.setString(1, r.getUuid());
//                    ps.setString(2, e.getKey().name());
//                    ps.setString(3, e.getValue());
//                    ps.addBatch();
//                }
//                ps.executeBatch();
//            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute(
                "SELECT * FROM resume r" +
                        "   LEFT JOIN contact c" +
                        "   ON r.uuid = c.resume_uuid" +
                        "   WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));

                    do {
                        if (rs.getString("resume_uuid") != null) {
                            String value = rs.getString("value");
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.addContact(type, value);
                        }
                    } while (rs.next());

                    return r;
                }
        );
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
        List<Resume> rl = sqlHelper.execute(
                "SELECT * FROM resume r" +
                        "   LEFT JOIN contact c" +
                        "   ON r.uuid = c.resume_uuid" +
                        "   ORDER BY r.full_name, r.uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        Resume r = map.get(rs.getString("uuid"));
                        if (r == null) {
                            r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                            map.put(rs.getString("uuid"), r);
                        }

                        if (rs.getString("resume_uuid") != null) {
                            r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        }
                        //resumeList.add(r);
                    }
                    //return resumeList;
                    return new ArrayList<>(map.values());
                }
        );
        return rl;
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
}