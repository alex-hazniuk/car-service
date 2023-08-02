package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;

public class RemoveGarage extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To remove a garage slot please enter its id: ");

        int id = scanner.nextInt();

        boolean removed = garageSlotService.remove(id);

        if(removed) {
            System.out.println("The garage slot was successfully deleted.");
        } else {
            System.out.println("Something went wrong... The garage slot wasn't deleted.");
        }
    }
}
