package org.example.repository;

import org.example.exception.InvalidIdException;
import org.example.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Order findById(Long id) {
        if (!orderMap.containsKey(id)) {
            throw new InvalidIdException("Can't find order dy id: " + id);
        }
        return orderMap.get(id);
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