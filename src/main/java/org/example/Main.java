package org.example;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(DataSource.getConnection()));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yml",
                new ClassLoaderResourceAccessor(),
                database);
        liquibase.update();

        TomcatLauncher.launch("8080");
//        Navigator navigator = new Navigator();
//        Builder builder = new Builder();
//
//        MenuController menuController = new MenuController(builder, navigator);
//        menuController.run();
    }
}
