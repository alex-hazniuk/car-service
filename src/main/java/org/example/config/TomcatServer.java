package org.example.config;

import lombok.SneakyThrows;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class TomcatServer {

    public static final String DEFAULT_HOST = "localhost";
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
    }
}
