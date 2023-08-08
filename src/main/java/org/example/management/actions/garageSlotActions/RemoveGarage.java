package org.example.management.actions.garageSlotActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class RemoveGarage implements Action {
    @Override
    public void execute() {
        System.out.println("To remove a garage slot please enter its id: ");

        int id = scanner.nextInt();

        boolean removed = false;
        try {
            removed = genericInit.getGarageSlotService().remove(id);
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }

        if (removed) {
            System.out.println("The garage slot was successfully deleted.");
        } else {
            System.out.println("Something went wrong... The garage slot wasn't deleted.");
        }
    }
}
