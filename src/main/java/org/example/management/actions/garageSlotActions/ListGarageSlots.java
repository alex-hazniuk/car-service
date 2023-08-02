package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;

public class ListGarageSlots extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println(garageSlotService.getAll());
    }
}
