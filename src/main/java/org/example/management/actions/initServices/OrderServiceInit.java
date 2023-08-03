package org.example.management.actions.initServices;

import org.example.repository.GarageSlotRepositoryImpl;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryImpl;
import org.example.repository.RepairerRepositoryImpl;
import org.example.service.*;

public abstract class OrderServiceInit {

    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotRepositoryImpl());

    private final RepairerService repairerService = new RepairerServiceImpl(new RepairerRepositoryImpl());

    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    protected OrderService orderService = new OrderServiceImpl(orderRepository, repairerService, garageSlotService);
}
