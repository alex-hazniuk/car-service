package org.example.management.menu;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    @Getter
    private final String name;

    @Getter
    private final List<MenuItem> menuItems = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    @Override
    public String toString() {
        return name + '\n' + menuItems;
    }
}
