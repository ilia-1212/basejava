package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T SqlExecute(SqlHelper<T> sqlh, String statement, String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            return sqlh.execute(connection, ps);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        String statement = "DELETE FROM resume";
        SqlExecute((connection, ps) -> {
            ps.execute();
            return null;
        }, statement, null);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        String statement = "UPDATE resume SET full_name=? WHERE uuid=?";
        SqlExecute((connection, ps) -> {
            ps.setString(2, r.getUuid());
            ps.setString(1, r.getFullName());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        }, statement, null);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        String statement = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        SqlExecute((connection, ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());

            ps.executeUpdate();
            return null;
        }, statement, r.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        String statement = "SELECT r.* FROM resume r WHERE r.uuid = ?";
        return SqlExecute((connection, ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, statement, null);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        String statement = "DELETE FROM resume WHERE uuid=?";
        SqlExecute((connection, ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, statement, null);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");

        String statement = "SELECT r.* FROM resume r ORDER BY r.uuid DESC";
        return SqlExecute((connection, ps) -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumeList;
        }, statement, null);
    }

    @Override
    public int size() {
        LOG.info("counting size");
        String statement = "SELECT count(*) AS COUNT FROM resume r";
        return SqlExecute((connection, ps) -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("error while counting resume rows");
            }
            return rs.getInt("COUNT");
        }, statement, null);
    }
}
