package org.example.service;

import org.example.dao.GarageSlotDaoImpl;
import org.example.dao.OrderRepository;
import org.example.dao.OrderRepositoryImplInMemory;
import org.example.dao.RepairerDaoImpl;
import org.example.enums.OrderStatus;
import org.example.enums.SortType;
import org.example.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class OrderServiceImplInMemory implements OrderService {

    private OrderRepository orderRepository = new OrderRepositoryImplInMemory();
    private RepairerService repairerService = new RepairerServiceImpl(new RepairerDaoImpl()); //TODO
    private GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotDaoImpl()); //TODO

    @Override
    public Order create(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> listOrders(SortType sortType) {
        return orderRepository.findAll().stream().sorted(sortType.getComparator()).toList();
    }

    @Override
    public Order assignGarageSlot(Long id, Integer garageSlotId) {
        Order order = findById(id);
        GarageSlot garageSlot = garageSlotService.findById(garageSlotId); //TODO
        if (garageSlot.getStatus() == GarageSlotStatus.UNAVAILABLE) {   // TODO
            throw new RuntimeException(); //TODO
        }
        garageSlotService.changeStatus(garageSlot);  //TODO
        order.setGarageSlot(garageSlot);
        return orderRepository.update(id, order);
    }

    @Override
    public Order assignRepairer(Long id, Integer repairerId) {
        Order order = findById(id);
        Repairer repairer = repairerService.findById(repairerId);
        if (repairer.getStatus() == RepairerStatus.BUSY) {   // TODO
            throw new RuntimeException(); //TODO
        }
        repairerService.changeStatus(repairer); //TODO
        order.getRepairers().add(repairer);
        return orderRepository.update(id, order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Order completeOrder(Long id) {
        Order order = findById(id);
        order.setCompletedAt(Optional.of(LocalDateTime.now()));
        order.setStatus(OrderStatus.COMPLETED);
        order.getRepairers().stream().forEach(x -> repairerService.changeStatus(x)); //TODO
        garageSlotService.changeStatus(order.getGarageSlot()); //TODO
        return orderRepository.update(id, order);
    }

    @Override
    public Order cancelOrder(Long id) {
        Order order = findById(id);
        order.setStatus(OrderStatus.CANCELED);
        if (!order.getRepairers().isEmpty()) {
            order.getRepairers().stream().forEach(x -> repairerService.changeStatus(x)); //TODO
        }
        if (order.getGarageSlot() != null) {
            garageSlotService.changeStatus(order.getGarageSlot()); //TODO
        }
        return orderRepository.update(id, order);
    }
}
