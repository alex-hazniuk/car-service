package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.example.model.*;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static OrderService INSTANCE;

    private final OrderRepository orderRepository;

    private final RepairerService repairerService;

    private final GarageSlotService garageSlotService;

    private OrderServiceImpl() {
        this.orderRepository = OrderRepositoryImpl.getInstance();
        this.repairerService = RepairerServiceImpl.getINSTANCE();
        this.garageSlotService = GarageSlotServiceImpl.getINSTANCE();
    }

    public static OrderService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new OrderServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public Order create(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setPrice((double) Math.round(Math.random() * 100000) / 100);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> listOrders(SortType sortType) {
        return orderRepository.findAll().stream().sorted(sortType.getComparator()).collect(Collectors.toList());
    }

    @Override
    public Order assignGarageSlot(Long id, Integer garageSlotId) {
        Order order = findById(id);
        GarageSlot garageSlot = garageSlotService.findById(garageSlotId);
        if (garageSlot.getStatus() == GarageSlotStatus.UNAVAILABLE) {
            throw new RuntimeException(); //TODO
        }
        garageSlotService.changeStatus(garageSlot);
        order.setGarageSlot(garageSlot);
        return orderRepository.update(id, order);
    }

    @Override
    public Order assignRepairer(Long id, Integer repairerId) {
        Order order = findById(id);
        Repairer repairer = repairerService.findById(repairerId);
        if (repairer.getStatus() == RepairerStatus.BUSY) {
            throw new RuntimeException(); //TODO
        }
        repairerService.changeStatus(repairer);
        order.getRepairers().add(repairer);
        return orderRepository.update(id, order);
    }

    @Override
    public Order findById(Long id) {
        try {
            return orderRepository.findById(id);
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
        return new Order();
    }

    @Override
    public Order completeOrder(Long id) {
        Order order = findById(id);
        order.setCompletedAt(Optional.of(LocalDateTime.now()));
        order.setStatus(OrderStatus.COMPLETED);
        order.getRepairers().forEach(repairerService::changeStatus);
        garageSlotService.changeStatus(order.getGarageSlot());
        return orderRepository.update(id, order);
    }

    @Override
    public Order cancelOrder(Long id) {
        Order order = findById(id);
        order.setStatus(OrderStatus.CANCELED);
        if (!order.getRepairers().isEmpty()) {
            order.getRepairers().forEach(repairerService::changeStatus);
        }
        if (order.getGarageSlot() != null) {
            garageSlotService.changeStatus(order.getGarageSlot());
        }
        return orderRepository.update(id, order);
    }
}
