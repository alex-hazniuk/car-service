package org.example;

import org.example.management.menu.Builder;
import org.example.management.menu.MenuController;
import org.example.management.menu.Navigator;

public class Main {
    public static void main(String[] args) {
        Navigator navigator = new Navigator();
        Builder builder = new Builder();

        MenuController menuController = new MenuController(builder, navigator);
        menuController.run();
    }
}
