package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();

    private static HikariDataSource ds;
    static {
        config.setJdbcUrl("jdbc:h2:C:\\Users\\Администратор\\IdeaProjects\\car-service\\default");
        config.setUsername("user");
        config.setPassword("pass");
        ds = new HikariDataSource(config);
    }

    private DataSource(){}
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
