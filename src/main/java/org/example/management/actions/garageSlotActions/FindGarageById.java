package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;
import org.example.model.GarageSlot;

public class FindGarageById extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To view the garage slot information please enter its id: ");

        int id = scanner.nextInt();

        GarageSlot garageSlot = garageSlotService.findById(id);
        if (garageSlot.getId() != 0) {
            System.out.println(garageSlot);
        }
    }
}
