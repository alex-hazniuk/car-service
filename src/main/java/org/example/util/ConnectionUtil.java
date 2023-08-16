package org.example.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String URL = "jdbc:h2:C:\\Users\\Alex\\IdeaProjects\\car-service\\db\\H2-car-service";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";
    private static final int MAX_POOL_SIZE = 10;
    private static final String DRIVER = "org.h2.Driver";
    private static final DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(MAX_POOL_SIZE);
        config.setDriverClassName(DRIVER);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            //if (connection == null || connection.isClosed()) {
                return dataSource.getConnection();
            //}
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection ", e);
        }
    }
}
