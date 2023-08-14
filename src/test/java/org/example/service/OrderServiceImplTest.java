package org.example.service;

import org.example.exception.InappropriateStatusException;
import org.example.exception.InvalidIdException;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.RepairerStatus;
import org.example.repository.GarageSlotRepository;
import org.example.repository.GarageSlotRepositoryImpl;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryImpl;
import org.example.repository.RepairerRepository;
import org.example.repository.RepairerRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository = new OrderRepositoryImpl(new HashMap<>());
    private RepairerRepository repairerRepository = new RepairerRepositoryImpl(new ArrayList<>());
    private GarageSlotRepository garageSlotRepository = new GarageSlotRepositoryImpl(new ArrayList<>());
    private GarageSlotService garageSlotService = new GarageSlotServiceImpl(garageSlotRepository);
    private RepairerService repairerService = new RepairerServiceImpl(repairerRepository);
    private OrderService orderService = new OrderServiceImpl(orderRepository, repairerService, garageSlotService);

    @Test
    void whenCreatingOrder_thenOrderShouldBeCreated() {
        Order order = new Order();
        Order createdOrder = orderService.create(order);
        assertThat(createdOrder).isEqualTo(order);
    }

    @Test
    void whenCancellingOrder_thenOrderStatusShouldBeCanceled() {
        Order order = new Order();
        Order created = orderService.create(order);
        orderService.cancelOrder(created.getId());
        assertThat(created.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test

    void whenFindingInvalidOrderId_thenInvalidIdExceptionShouldBeThrown() {
        Order order = new Order();
        Order createdOrder = orderService.create(order);
        assertThatThrownBy(() -> orderService.findById(2L))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("Can't find order by id: 2");
    }

    @Test
    void whenAddingRepairerWithInvalidStatus_thenInappropriateStatusExceptionShouldBeThrown() {
        repairerService.save("John");
        repairerService.changeStatus(1);
        orderService.create(new Order());
        assertThatThrownBy(() -> orderService.assignRepairer(1L, 1))
                .isInstanceOf(InappropriateStatusException.class)
                .hasMessage("Repairer status is BUSY. Choose another one!");
    }

    @Test
    void whenCompletingOrder_thenRepairerStatusShouldBeAvailableAndOrderStatusShouldBeCompleted() {
        repairerService.save("John");
        repairerService.save("Nick");
        repairerService.save("Donald");
        orderService.create(new Order());
        orderService.assignRepairer(1L, 1);
        orderService.assignRepairer(1L, 2);
        orderService.assignRepairer(1L, 3);
        orderService.completeOrder(1L);
        Order order = orderService.findById(1L);
        assertThat(order.getRepairers()).allMatch(r -> r.getStatus() == RepairerStatus.AVAILABLE);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void whenCancellingOrder_thenRepairerStatusShouldBeAvailableAndOrderStatusShouldBeCanceled() {
        repairerService.save("Rob");
        repairerService.save("Phil");
        orderService.create(new Order());
        orderService.assignRepairer(1L, 1);
        orderService.assignRepairer(1L, 2);
        orderService.cancelOrder(1L);
        Order order = orderService.findById(1L);
        assertThat(order.getRepairers()).allMatch(r -> r.getStatus() == RepairerStatus.AVAILABLE);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }
}
