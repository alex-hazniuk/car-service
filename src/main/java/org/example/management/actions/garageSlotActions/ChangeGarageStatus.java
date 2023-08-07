package org.example.management.actions.garageSlotActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;

public class ChangeGarageStatus extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To change garage slot status please enter its id: \n ID: ");

        int id = scanner.nextInt();

        try {
            System.out.println(garageSlotService.changeStatus(id));
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Garage slot status was successfully updated.");
    }
}
