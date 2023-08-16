package org.example;

import org.apache.catalina.LifecycleException;
import org.example.model.Repairer;
import org.example.repository.jdbc_repository.JDBCRepairerRepository;
import org.example.repository.jdbc_repository.JDBCRepairerRepositoryImpl;
import org.example.service.JDBCService.JDBCRepairerService;
import org.example.service.JDBCService.JDBCRepairerServiceImpl;

import javax.servlet.ServletException;

public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException {
        //TomcatLauncher.launch("8080");
//        Navigator navigator = new Navigator();
//        Builder builder = new Builder();
//
//        MenuController menuController = new MenuController(builder, navigator);
//        menuController.run();
        JDBCRepairerService  service = new JDBCRepairerServiceImpl(new JDBCRepairerRepositoryImpl()) ;
        //service.save("Alex");
        service.remove(6);
        //service.changeStatus(4);
        System.out.println(service.sortedByName());
    }
}
