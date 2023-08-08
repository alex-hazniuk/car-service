package org.example.management.actions.garageSlotActions;

import org.example.management.actions.Action;

public class ListGarageSlots implements Action {
    @Override
    public void execute() {
        System.out.println(genericInit.getGarageSlotService().getAll());
    }
}
