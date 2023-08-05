package org.example.management.actions;

public class Exit implements Action {
    @Override
    public void execute() {
        System.exit(0);
    }
}
