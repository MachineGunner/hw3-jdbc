package org.olha_b.util;

import org.olha_b.exception.DataProcessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES = new Properties();

    private ConnectionUtil() {
    }

    static {
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "root");

        try {
            Class.forName(DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new DataProcessException("Cannot load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessException("Unable to get connection to the database", e);
        }
    }
}
