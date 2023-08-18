package org.example;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.settings.PropertiesUtil;


public class Main {
    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSource();

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yml",
                new ClassLoaderResourceAccessor(),
                database);
        liquibase.update();

        TomcatLauncher.launch("8080");
    }
}
