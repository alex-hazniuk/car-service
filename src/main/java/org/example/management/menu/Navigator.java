package org.example.management.menu;

import java.util.stream.Collectors;

public class Navigator {

    private Menu currentMenu;

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        System.out.println(currentMenu.getName());
        currentMenu.getMenuItems().stream()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public void navigate(int index) {
        if(currentMenu != null) {
            MenuItem menuItem = currentMenu.getMenuItems().get(index-1);
            if(menuItem.getAction() != null) {
                menuItem.doAction();
            }
            currentMenu = menuItem.getNextMenu();
        }
    }
}
