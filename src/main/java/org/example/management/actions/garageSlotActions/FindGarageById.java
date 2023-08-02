package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;

public class FindGarageById extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To view the garage slot information please enter its id: ");

        int id = scanner.nextInt();

        System.out.println(garageSlotService.findById(id));
    }
}
