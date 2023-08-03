package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;


public class AddGarage extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {

        garageSlotService.save();

        System.out.println("The garage slot was successfully added.");
    }
}
