package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();

    private static String url;

    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    private final HikariDataSource ds;

    public DataSource() {
        if (url == null) {
            url = "jdbc:h2:./default";
        }
        config.setJdbcUrl(url);
        config.setUsername("user");
        config.setPassword("pass");
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
