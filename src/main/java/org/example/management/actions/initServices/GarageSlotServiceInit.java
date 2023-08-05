package org.example.management.actions.initServices;

import org.example.repository.GarageSlotRepositoryImpl;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

public abstract class GarageSlotServiceInit {
    protected GarageSlotService garageSlotService = GarageSlotServiceImpl.getINSTANCE();
}
