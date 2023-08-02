package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.GarageSlotServiceInit;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;

public class ChangeGarageStatus extends GarageSlotServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To change garage slot status please enter repairers id: \n ID: ");

        int id = scanner.nextInt();

        System.out.println("""
                Please select a new repairer status :\s
                 1. Available
                 2. Unavailable""");
        int status = scanner.nextInt();

        GarageSlot garageSlot = new GarageSlot();
        garageSlot.setId(id);

        if(status == 1) {
            garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
        } else {
            garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
        }

        System.out.println(garageSlotService.changeStatus(garageSlot));
        System.out.println("Garage slot status was successfully updated.");
    }
}
