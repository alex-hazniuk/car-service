package org.example.management.actions.initServices;

import org.example.repository.GarageSlotRepositoryImpl;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryImpl;
import org.example.repository.RepairerRepositoryImpl;
import org.example.service.*;

public abstract class OrderServiceInit {

    protected OrderService orderService = OrderServiceImpl.getINSTANCE();
}
