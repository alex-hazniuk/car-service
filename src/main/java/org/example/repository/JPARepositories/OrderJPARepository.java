package org.example.repository.JPARepositories;

import jakarta.persistence.EntityManager;
import org.example.config.HibernateUtil;
import org.example.model.Order;
import org.example.model.OrderStatus;

import org.example.repository.OrderRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class OrderJPARepository implements OrderRepository {

    private final EntityManager em = HibernateUtil.getEntityManager();
    @Override
    public Order save(Order order) {

        /*OrderEntity orderEntity = new OrderEntity();
        orderEntity.setRepairers(new HashSet<>());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setCreatedAt(order.getCreatedAt());*/

        em.getTransaction().begin();
        em.persist(order);
        em.flush();
        em.getTransaction().commit();

        //order.setId(order.getId());
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
        List<Order> orders = (List<Order>) em.createQuery(hql).getResultList();
        return orders;
    }

    @Override
    public List<Order> findAllSortedByStatus() {
        String hql = "FROM Order ORDER BY status";
        List<Order> orders = (List<Order>) em.createQuery(hql).getResultList();
        return orders;
    }

    @Override
    public List<Order> findAllSortedByPrice() {
        String hql = "FROM Order ORDER BY price";
        List<Order> orders = (List<Order>) em.createQuery(hql).getResultList();
        return orders;
    }

    @Override
    public List<Order> findAllSortedByCreatedDate() {
        String hql = "FROM Order ORDER BY createdAt";
        List<Order> orders = (List<Order>) em.createQuery(hql).getResultList();
        return orders;
    }

    @Override
    public List<Order> findAllSortedByCompletedDate() {
        String hql = "FROM Order ORDER BY completedAt";
        List<Order> orders = (List<Order>) em.createQuery(hql).getResultList();
        return orders;
    }

    @Override
    public Order update(Long id, Order order) {
        order.setId(id);
        return save(order);
    }
}
