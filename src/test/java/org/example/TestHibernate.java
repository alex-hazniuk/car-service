package org.example;

import jakarta.persistence.EntityManager;
import org.example.config.HibernateUtil;
import org.example.model.OrderStatus;
import org.example.model.entity.OrderEntity;
import org.junit.jupiter.api.Test;

public class TestHibernate {

    @Test
    public void testHiber() {
        EntityManager em = HibernateUtil.getEntityManager();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderStatus.IN_PROGRESS);
        orderEntity.setPrice(500.0);
        System.out.println(orderEntity.getId());

        em.getTransaction().begin();
        em.persist(orderEntity);

        em.getTransaction().commit();
        System.out.println(orderEntity.getId());
    }
}
