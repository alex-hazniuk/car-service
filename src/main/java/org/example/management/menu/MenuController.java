package org.example.management.menu;

import java.util.Scanner;

public class MenuController {

    private final Builder builder;

    private final Navigator navigator;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {

        builder.buildMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int index = scanner.nextInt();
            navigator.navigate(index);
            navigator.printMenu();
        }
    }
}
