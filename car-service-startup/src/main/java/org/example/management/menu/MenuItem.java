package org.example.management.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.management.actions.Action;

@AllArgsConstructor
public class MenuItem {

    private int id;
    private String title;
    @Getter
    private Action action;
    @Getter
    private Menu nextMenu;

    public void doAction() {
        action.execute();
    }

    @Override
    public String toString() {
        return id +
                "." + title;
    }
}
