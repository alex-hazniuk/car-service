package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;

public class ListRepairers extends Action {
    @Override
    public void execute() {
        System.out.println(genericInit.getRepairerService().getAll());
    }
}
