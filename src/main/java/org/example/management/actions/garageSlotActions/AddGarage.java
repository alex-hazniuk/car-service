package org.example.management.actions.garageSlotActions;

import org.example.management.actions.ActionWithProperties;


public class AddGarage extends ActionWithProperties {

    @Override
    public void execute() {

        if (propertySettings.isGarageSlotAdditionEnabled()) {
            genericInit.getGarageSlotService().save();
            System.out.println("The garage slot was successfully added.");
        } else {
            System.out.println("The garage slot adding is not available");
        }
    }
}