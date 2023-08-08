package org.example.repository;

import org.example.model.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private final Map<Long, Order> orderMap;

    private Long idCounter;

    public OrderRepositoryImpl(Map<Long, Order> orderMap) {
        this.orderMap = orderMap;
        this.idCounter = (long) orderMap.size();
    }

    @Override
    public Order save(Order order) {
        order.setId(getIdCounter());
        orderMap.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orderMap.get(id));
    }

    @Override
    public List<Order> findAll() {
        return orderMap.values().stream().toList();
    }

    @Override
    public Map<Long, Order> findAllMapFormat() {
        return orderMap;
    }

    @Override
    public Order update(Long id, Order order) {
        orderMap.put(id, order);
        return order;
    }

    public Long getIdCounter() {
        return ++idCounter;
    }
}
