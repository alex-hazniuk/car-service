package org.example.FileRepositories;

import org.example.OrderRepository;
import org.example.model.Order;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderFileRepository implements OrderRepository {

    private final CarServiceStoreHandler carServiceStoreHandler;

    private final Long idCounter;

    public OrderFileRepository(CarServiceStoreHandler carServiceStoreHandler) {
        this.carServiceStoreHandler = carServiceStoreHandler;
        this.idCounter = (long) carServiceStoreHandler.read().orders().size();
    }

    @Override
    public Order save(Order order) {
        State state = carServiceStoreHandler.read();
        Map<Long, Order> orders = state.orders();
        order.setId(idCounter);
        orders.put(idCounter, order);
        carServiceStoreHandler.write(state.withOrders(orders));

        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return findAll().stream().filter(order -> (order.getId().equals(id))).findAny();
    }

    @Override
    public List<Order> findAll() {
        return carServiceStoreHandler.read().orders().values().stream().toList();
    }

    @Override
    public List<Order> findAllSortedByStatus() {
        return findAll().stream()
                .sorted(Comparator.comparing(Order::getStatus))
                .toList();
    }

    @Override
    public List<Order> findAllSortedByPrice() {
        return findAll().stream()
                .sorted(Comparator.comparing(Order::getPrice))
                .toList();
    }

    @Override
    public List<Order> findAllSortedByCreatedDate() {
        return findAll().stream()
                .sorted(Comparator.comparing(Order::getCreatedAt))
                .toList();
    }

    @Override
    public List<Order> findAllSortedByCompletedDate() {
        return findAll().stream()
                .sorted(Comparator.comparing(Order::getCompletedAt))
                .toList();
    }

    @Override
    public Order update(Long id, Order order) {
        State state = carServiceStoreHandler.read();
        Map<Long, Order> orders = state.orders();
        orders.put(id, order);
        carServiceStoreHandler.write(state.withOrders(orders));

        return order;
    }
}
