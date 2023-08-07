package org.example.management.actions.garageSlotActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;

public class FindGarageById extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To view the garage slot information please enter its id: ");

        int id = scanner.nextInt();

        try {
            System.out.println(garageSlotService.findById(id));
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
    }
}
