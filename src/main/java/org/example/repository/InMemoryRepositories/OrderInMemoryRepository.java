package org.example.repository.InMemoryRepositories;

import org.example.model.Order;
import org.example.repository.OrderRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderInMemoryRepository implements OrderRepository {

    private final Map<Long, Order> orderMap;

    private Long idCounter;

    public OrderInMemoryRepository(Map<Long, Order> orderMap) {
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
    public Order update(Long id, Order order) {
        orderMap.put(id, order);
        return order;
    }

    public Long getIdCounter() {
        return ++idCounter;
    }
}
