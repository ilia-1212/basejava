package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.SqlExecute(
                "DELETE FROM resume",
                ps -> {
                ps.execute();
                return null;
            }
        );
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.SqlExecute(
                "UPDATE resume SET full_name=? WHERE uuid=?",
                ps -> {
                    ps.setString(2, r.getUuid());
                    ps.setString(1, r.getFullName());
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.SqlExecute(
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.executeUpdate();
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.SqlExecute(
                "SELECT r.* FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                }
        );
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.SqlExecute(
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
        return sqlHelper.SqlExecute(
                "SELECT r.* FROM resume r ORDER BY r.full_name, r.uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumeList = new ArrayList<>();
                    while (rs.next()) {
                        resumeList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return resumeList;
                }
        );
    }

    @Override
    public int size() {
        LOG.info("counting size");
        return sqlHelper.SqlExecute(
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
