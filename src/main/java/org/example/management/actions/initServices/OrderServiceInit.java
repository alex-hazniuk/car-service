package org.example.management.actions.initServices;

import org.example.dao.GarageSlotDaoImpl;
import org.example.dao.OrderRepository;
import org.example.dao.OrderRepositoryImplInMemory;
import org.example.dao.RepairerDaoImpl;
import org.example.service.*;

public abstract class OrderServiceInit {

    private final GarageSlotDaoImpl garageSlotDao = new GarageSlotDaoImpl();
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(garageSlotDao);

    private final RepairerDaoImpl repairerDao = new RepairerDaoImpl();
    private final RepairerService repairerService = new RepairerServiceImpl(repairerDao);

    private final OrderRepository orderRepository = new OrderRepositoryImplInMemory();
    protected OrderService orderService = new OrderServiceImplInMemory(orderRepository, repairerService, garageSlotService);
}
