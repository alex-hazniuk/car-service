package org.example.JPARepositories;

import org.example.HibernateUtil;
import org.example.OrderRepository;
import org.example.model.Order;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class OrderJPARepository implements OrderRepository {
    private final EntityManager em = HibernateUtil.getInstance().createEntityManager();

    @Override
    public Order save(Order order) {
        em.getTransaction().begin();
        em.persist(order);
        em.flush();
        em.getTransaction().commit();
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        em.find(Order.class, id);
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public List<Order> findAll() {
        String hql = "FROM Order";
        return (List<Order>) em.createQuery(hql).getResultList();
    }

    @Override
    public List<Order> findAllSortedByStatus() {
        String hql = "FROM Order ORDER BY status";
        return (List<Order>) em.createQuery(hql).getResultList();
    }

    @Override
    public List<Order> findAllSortedByPrice() {
        String hql = "FROM Order ORDER BY price";
        return (List<Order>) em.createQuery(hql).getResultList();
    }

    @Override
    public List<Order> findAllSortedByCreatedDate() {
        String hql = "FROM Order ORDER BY createdAt";
        return (List<Order>) em.createQuery(hql).getResultList();
    }

    @Override
    public List<Order> findAllSortedByCompletedDate() {
        String hql = "FROM Order ORDER BY completedAt";
        return (List<Order>) em.createQuery(hql).getResultList();
    }

    @Override
    public Order update(Long id, Order order) {
        order.setId(id);
        em.getTransaction().begin();
        em.merge(order);
        em.getTransaction().commit();
        return order;
    }
}
