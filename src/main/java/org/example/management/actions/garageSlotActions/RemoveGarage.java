package org.example.management.actions.garageSlotActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.ActionWithProperties;

public class RemoveGarage extends ActionWithProperties {
    @Override
    public void execute() {
        if (propertySettings.isGarageSlotDeletionEnabled()) {
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
        } else {
            System.out.println("The garage slot deleting is not available");
        }
    }
}
