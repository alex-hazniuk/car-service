package org.example.repository;

import org.example.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private static final Map<Long, Order> orderMap = new HashMap<>();

    private Long idCounter = 0L;

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
    public Order update(Long id, Order order) {
        orderMap.put(id, order);
        return order;
    }

    public Long getIdCounter() {
        return ++idCounter;
    }
}
