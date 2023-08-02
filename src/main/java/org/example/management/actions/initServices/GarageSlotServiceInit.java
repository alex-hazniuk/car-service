package org.example.management.actions.initServices;

import org.example.dao.GarageSlotDaoImpl;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

public abstract class GarageSlotServiceInit {
    private final GarageSlotDaoImpl garageSlotDao = new GarageSlotDaoImpl();
    protected GarageSlotService garageSlotService = new GarageSlotServiceImpl(garageSlotDao);
}
