package org.example.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory instance = initEntityManagerFactory();

    public HibernateUtil() {
    }

    private static EntityManagerFactory initEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("service");
    }

    public static EntityManagerFactory getInstance() {
        return instance;
    }
}
