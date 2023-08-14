package org.example;

import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;

public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException {
        TomcatLauncher.launch("8080");
//        Navigator navigator = new Navigator();
//        Builder builder = new Builder();
//
//        MenuController menuController = new MenuController(builder, navigator);
//        menuController.run();
    }
}
