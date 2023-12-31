package org.example.management.actions.garageSlotActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class ChangeGarageStatus extends Action {
    @Override
    public void execute() {
        System.out.println("To change garage slot status please enter its id: \n ID: ");

        int id = scanner.nextInt();

        try {
            System.out.println(genericInit.getGarageSlotService().changeStatus(id));
            System.out.println("Garage slot status was successfully updated.");
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
    }
}
