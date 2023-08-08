package org.example.management.actions.initServices;

import org.example.management.serialization.GarageSlotSerialization;
import org.example.management.serialization.OrderSerialization;
import org.example.management.serialization.RepairerSerialization;
import org.example.repository.*;
import org.example.service.*;

public class GenericInit {

    private final GarageSlotSerialization garageSlotSerialization = new GarageSlotSerialization();

    private final RepairerSerialization repairerSerialization = new RepairerSerialization();

    private final OrderSerialization orderSerialization = new OrderSerialization();


    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(
            new GarageSlotRepositoryImpl(garageSlotSerialization.readList()));


    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerRepositoryImpl(repairerSerialization.readList()));


    private final OrderRepository orderRepository = new OrderRepositoryImpl(orderSerialization.readList());


    protected OrderService orderService = new OrderServiceImpl(orderRepository,
            repairerService, garageSlotService);


    public final RepairerService getRepairerService() {
        return repairerService;
    }


    public final GarageSlotService getGarageSlotService() {
        return garageSlotService;
    }


    public final OrderService getOrderService() {
        return orderService;
    }

    public final GarageSlotSerialization getGarageSlotSerialization() {
        return garageSlotSerialization;
    }

    public final OrderSerialization getOrderSerialization() {
        return orderSerialization;
    }

    public final RepairerSerialization getRepairerSerialization() {
        return repairerSerialization;
    }
}
