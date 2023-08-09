package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;


public class AddGarage implements Action {
    @Override
    public void execute() {

        genericInit.getGarageSlotService().save();

        System.out.println("The garage slot was successfully added.");
    }
}
