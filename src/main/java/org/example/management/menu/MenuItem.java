package org.example.management.menu;

import org.example.management.actions.Action;

public class MenuItem {

    private int id;
    private String title;
    private Action action;
    private Menu nextMenu;

    public MenuItem(int id, String title, Action action, Menu nextMenu) {
        this.id = id;
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    public void doAction() {
        action.execute();
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return id +
                "." + title;
    }
}
