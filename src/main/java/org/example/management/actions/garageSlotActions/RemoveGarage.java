package org.example.management.actions.garageSlotActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class RemoveGarage extends Action {
    @Override
    public void execute() {
        System.out.println("To remove a garage slot please enter its id: ");

        int id = scanner.nextInt();

        boolean removed = false;
        try {
            removed = genericInit.getGarageSlotService().remove(id);
            System.out.println("The garage slot was successfully deleted.");
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }

        if (!removed) {
            System.out.println("Something went wrong... The garage slot wasn't deleted.");
        }
    }
}
