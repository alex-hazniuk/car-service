package org.example.dao;

import org.example.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderRepositoryImplInMemory implements OrderRepository {

    private Map<Long, Order> orderMap = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public Order save(Order order) {
        order.setId(getIdCounter());
        orderMap.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderMap.containsKey(id) ? Optional.of(orderMap.get(id)) : Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return orderMap.values().stream().toList();
    }

    @Override
    public Order update(Long id, Order order) {
        return orderMap.put(id, order);
    }

    public Long getIdCounter() {
        return ++idCounter;
    }
}
