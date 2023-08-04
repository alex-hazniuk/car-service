package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;

public class ChangeGarageStatus extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To change garage slot status please enter repairers id: \n ID: ");

        int id = scanner.nextInt();

        System.out.println(garageSlotService.changeStatus(id));
        System.out.println("Garage slot status was successfully updated.");
    }
}
