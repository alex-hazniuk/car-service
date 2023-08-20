package org.example.config;

import lombok.SneakyThrows;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.example.servlets.order_servlets.*;

import java.io.File;

public class TomcatServer {

    public static final String WEBAPP_DIR = "src/main/webapp/";
    public static final String PORT = "8080";
    public static final String TARGET_CLASSES = "target/classes";// "target/tomcat";
    public static final String CONTEXT_PATH = "/";

    @SneakyThrows
    public static void start() {
        Tomcat tomcat = new Tomcat();

        Connector connector = new Connector();
        connector.setPort(Integer.parseInt(PORT));

        tomcat.setConnector(connector);
        tomcat.setBaseDir(new File(TARGET_CLASSES).getAbsolutePath());

        Context context = tomcat.addWebapp(CONTEXT_PATH, new File(WEBAPP_DIR).getAbsolutePath());
        addServletsToTomcat(tomcat, context);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static void addServletsToTomcat(Tomcat tomcat, Context context) {

        tomcat.addServlet("", "AllOrdersServlet", new AllOrdersServlet());
        context.addServletMappingDecoded("/orders/all", "AllOrdersServlet");

        tomcat.addServlet("", "CreateOrderServlet", new CreateOrderServlet());
        context.addServletMappingDecoded("/orders/create", "CreateOrderServlet");

        tomcat.addServlet("", "AllOrdersOrderByCompletedDateServlet", new AllOrdersOrderByCompletedDateServlet());
        context.addServletMappingDecoded("/orders/all/sort/completed", "AllOrdersOrderByCompletedDateServlet");

        tomcat.addServlet("", "AllOrdersOrderByCreatedDateServlet", new AllOrdersOrderByCreatedDateServlet());
        context.addServletMappingDecoded("/orders/all/sort/created", "AllOrdersOrderByCreatedDateServlet");

        tomcat.addServlet("", "AllOrdersOrderByPriceServlet", new AllOrdersOrderByPriceServlet());
        context.addServletMappingDecoded("/orders/all/sort/price", "AllOrdersOrderByPriceServlet");

        tomcat.addServlet("", "AllOrdersOrderByStatusServlet", new AllOrdersOrderByStatusServlet());
        context.addServletMappingDecoded("/orders/all/sort/status", "AllOrdersOrderByStatusServlet");

        tomcat.addServlet("", "AssignGarageSlotServlet", new AssignGarageSlotServlet());
        context.addServletMappingDecoded("/orders/assign-garage-slot/*", "AssignGarageSlotServlet");

        tomcat.addServlet("", "AssignRepairerServlet", new AssignRepairerServlet());
        context.addServletMappingDecoded("/orders/assign_repairer", "AssignRepairerServlet");

        tomcat.addServlet("", "CancelOrderServlet", new CancelOrderServlet());
        context.addServletMappingDecoded("/orders/cansel/*", "CancelOrderServlet");

        tomcat.addServlet("", "CompleteOrderServlet", new CompleteOrderServlet());
        context.addServletMappingDecoded("/orders/complete/*", "CompleteOrderServlet");

        tomcat.addServlet("", "GetOrderServlet", new GetOrderServlet());
        context.addServletMappingDecoded("/orders", "GetOrderServlet");

    }

   /* public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8080;
    public static final String CONTEXT_PATH = "/car-service";
    public static final String DOC_BASE = ".";
    public static final String WEB_INF_CLASSES = "target/classes";
    public static final String WEB_APP_MOUNT = "/WEB-INF/classes";
    public static final String INTERNAL_PATH = "/";

    @SneakyThrows
    public static void start() {
        Tomcat tomcat = new Tomcat();
        tomcat.setHostname(DEFAULT_HOST);
        tomcat.getHost().setAppBase(DOC_BASE);
        tomcat.setPort(DEFAULT_PORT);
        tomcat.getConnector();
        Context context = tomcat.addWebapp(CONTEXT_PATH, DOC_BASE);
        File classes = new File(WEB_INF_CLASSES);
        String base = classes.getAbsolutePath();
        StandardRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, WEB_APP_MOUNT, base, INTERNAL_PATH));
        context.setResources(resources);
        tomcat.start();
        tomcat.getServer().await();
    }*/
}
