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
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderService orderService;
    private GarageSlotService garageSlotService;
    private RepairerService repairerService;
    private RepairerRepository repairerRepository;
    private GarageSlotRepository garageSlotRepository;

    @BeforeEach
    void init() {
        orderRepository = new OrderRepositoryImpl();
        repairerRepository = new RepairerRepositoryImpl();
        garageSlotRepository = new GarageSlotRepositoryImpl();
        garageSlotService = new GarageSlotServiceImpl(garageSlotRepository);
        repairerService = new RepairerServiceImpl(repairerRepository);
        orderService = new OrderServiceImpl(orderRepository, repairerService, garageSlotService);
    }

    @AfterEach
    public void tearDown() {
        repairerService.getAll().clear();
    }
    @Test
    void testOrderCreation() {
        Order order = new Order();
        Order createdOrder = orderService.create(order);
        assertEquals(order, createdOrder);
    }

    @Test
    void testOrderStatusAfterCancelling() {
        Order order = new Order();
        Order created = orderService.create(order);
        orderService.cancelOrder(created.getId());
        assertEquals(OrderStatus.CANCELED, created.getStatus());
    }

    @Test
    void testFindByInvalidOrderId() {
        Order order = new Order();
        Order createdOrder = orderService.create(order);
        InvalidIdException invalidIdException = assertThrows(InvalidIdException.class, () -> orderService.findById(2L));
        assertEquals("Can't find order by id: 2", invalidIdException.getMessage());
    }

    @Test
    void testAddingRepairerWithInvalidStatus() {
        repairerService.save("John");
        repairerService.changeStatus(1);
        orderService.create(new Order());
        InappropriateStatusException inappropriateStatusException = assertThrows(InappropriateStatusException.class, () -> orderService.assignRepairer(1L, 1));
        assertEquals("Repairer status is BUSY. Choose another one!", inappropriateStatusException.getMessage());
    }


    @Test
    void testCompletedOrder(){
        repairerService.save("John");
        repairerService.save("Nick");
        repairerService.save("Donald");
        orderService.create(new Order());
        orderService.assignRepairer(1L,1);
        orderService.assignRepairer(1L,2);
        orderService.assignRepairer(1L,3);
        orderService.completeOrder(1L);
        Order order = orderService.findById(1L);
        order.getRepairers().forEach(r->assertEquals(RepairerStatus.AVAILABLE,r.getStatus()));
        assertEquals(OrderStatus.COMPLETED,order.getStatus());
    }


    @Test
    void testCanceledOrder(){
        repairerService.save("Rob");
        repairerService.save("Phil");
        orderService.create(new Order());
        orderService.assignRepairer(1L,1);
        orderService.assignRepairer(1L,2);
        orderService.cancelOrder(1L);
        Order order = orderService.findById(1L);
        order.getRepairers().forEach(r->assertEquals(RepairerStatus.AVAILABLE,r.getStatus()));
        assertEquals(OrderStatus.CANCELED,order.getStatus());
    }

}
