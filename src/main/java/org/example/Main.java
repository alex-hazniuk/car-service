package org.example;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.config.DataSource;
import org.example.config.TomcatLauncher;
import org.example.repository.JPARepositories.RepairerJPARepository;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;


public class Main {
    public static void main(String[] args) throws Exception {
        /*DataSource dataSource = new DataSource();

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yml",
                new ClassLoaderResourceAccessor(),
                database);
        liquibase.update();

        TomcatLauncher.launch("8080");*/
        RepairerService repairerService = new RepairerServiceImpl(new RepairerJPARepository());
        //System.out.println(repairerService.save("Svetlana"));
        //System.out.println(repairerService.findById(1));
        System.out.println(repairerService.getAll());
    }
}
