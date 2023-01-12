package com.urise.webapp.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlHelper<T> {
    T execute(Connection connection, PreparedStatement ps) throws SQLException;
}
