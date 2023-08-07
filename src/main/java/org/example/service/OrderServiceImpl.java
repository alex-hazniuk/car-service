package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.InappropriateStatusException;
import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RepairerService repairerService;

    private final GarageSlotService garageSlotService;

    @Override
    public Order create(Order order) {
        order.setRepairers(new HashSet<>());
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setPrice((double) Math.round(Math.random() * 100000) / 100);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> listOrders(SortType sortType) {
        return orderRepository.findAll().stream()
                .sorted(sortType.getComparator())
                .toList();
    }

    @Override
    public Order assignGarageSlot(Long id, Integer garageSlotId) {
        Order order = findById(id);
        GarageSlot garageSlot = garageSlotService.findById(garageSlotId);
        if (garageSlot.getStatus() == GarageSlotStatus.UNAVAILABLE) {
            throw new InappropriateStatusException(
                    "Garage status is UNAVAILABLE. Choose another one!");
        }
        garageSlotService.changeStatus(garageSlotId);
        order.setGarageSlot(garageSlot);
        return orderRepository.update(id, order);
    }

    @Override
    public Order assignRepairer(Long id, Integer repairerId) {
        Order order = findById(id);
        Repairer repairer = repairerService.findById(repairerId);
        if (repairer.getStatus() == RepairerStatus.BUSY) {
            throw new InappropriateStatusException(
                    "Repairer status is BUSY. Choose another one!");
        }
        repairerService.changeStatus(repairerId);
        order.getRepairers().add(repairer);
        return orderRepository.update(id, order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new InvalidIdException("Can't find order by id: " + id));
    }

    @Override
    public Order completeOrder(Long id) {
        Order order = findById(id);
        order.setCompletedAt(Optional.of(LocalDateTime.now()));
        order.setStatus(OrderStatus.COMPLETED);
        order.getRepairers()
                .forEach(repairer -> repairerService.changeStatus(repairer.getId()));
        if (order.getGarageSlot() != null) {
            garageSlotService.changeStatus(order.getGarageSlot().getId());
        }

        return orderRepository.update(id, order);
    }

    @Override
    public Order cancelOrder(Long id) {
        Order order = findById(id);
        order.setStatus(OrderStatus.CANCELED);
        if (!order.getRepairers().isEmpty()) {
            order.getRepairers().forEach(repairer -> repairerService.changeStatus(repairer.getId()));
        }
        if (order.getGarageSlot() != null) {
            garageSlotService.changeStatus(order.getGarageSlot().getId());
        }
        return orderRepository.update(id, order);
    }
}
