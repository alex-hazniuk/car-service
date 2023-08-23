package org.example.repository;

import org.example.OrderRepository;
import org.example.jpaRepository.OrderSpringJPARepository;
import org.example.model.Order;

import java.util.List;
import java.util.Optional;

public class OrderJPARepository implements OrderRepository {

    private final OrderSpringJPARepository repository;

    public OrderJPARepository(OrderSpringJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> findAllSortedByStatus() {
        return repository.findAllByOrderByStatus();
    }

    @Override
    public List<Order> findAllSortedByPrice() {
        return repository.findAllByOrderByPrice();
    }

    @Override
    public List<Order> findAllSortedByCreatedDate() {
        return repository.findAllByOrderByCreatedAt();
    }

    @Override
    public List<Order> findAllSortedByCompletedDate() {
        return repository.findAllByOrderByCompletedAt();
    }

    @Override
    public Order update(Long id, Order order) {
        return repository.save(order);
    }
}
