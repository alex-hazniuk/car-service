package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.*;
import org.example.repository.*;
import org.example.service.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

class OrderServiceImplTest {

    OrderRepository orderRepository;
    OrderService orderService;
    GarageSlotService garageSlotService;
    RepairerService repairerService;
    RepairerRepository repairerRepository;
    GarageSlotRepository garageSlotRepository;

    @BeforeEach
    void init() {
        orderRepository = new OrderRepositoryImpl();
        repairerRepository = new RepairerRepositoryImpl();
        garageSlotRepository = new GarageSlotRepositoryImpl();
        garageSlotService = new GarageSlotServiceImpl(garageSlotRepository);
        repairerService = new RepairerServiceImpl(repairerRepository);
        orderService = new OrderServiceImpl(orderRepository, repairerService, garageSlotService);
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
        assertEquals(OrderStatus.CANCELED,created.getStatus());
    }

    @Test
    void testFindByInvalidOrderId(){
        Order order = new Order();
        Order createdOrder = orderService.create(order);
        InvalidIdException invalidIdException =  assertThrows(InvalidIdException.class,() ->  orderService.findById(2L));
        assertEquals("Can't find order by id: 2", invalidIdException.getMessage());
    }



}
