package org.example;

import org.hibernate.cfg.AvailableSettings;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Map;

public class HibernateUtil {
    private static final EntityManagerFactory instance = initEntityManagerFactory();

    public HibernateUtil() {
    }

    private static EntityManagerFactory initEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(
                "service",
            Map.of(
                    AvailableSettings.CLASSLOADERS,
                    List.of(Thread.currentThread().getContextClassLoader())
            )
        );
    }

    public static EntityManagerFactory getInstance() {
        return instance;
    }
}
