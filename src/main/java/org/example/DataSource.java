package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.settings.PropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();

    private static String url;
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";


    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    private final HikariDataSource ds;



//    static {
//        if (url == null) {
//            url = "jdbc:h2:./default";
//        }
//        config.setJdbcUrl(url);
//        config.setUsername("user");
//        config.setPassword("pass");
//        ds = new HikariDataSource(config);
//    }

    public DataSource() {
        if (url == null) {
            url = PropertiesUtil.getProperty("db.url");
        }
        config.setJdbcUrl(url);
        config.setUsername(PropertiesUtil.getProperty(USERNAME_KEY));
        config.setPassword(PropertiesUtil.getProperty(PASSWORD_KEY));
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
