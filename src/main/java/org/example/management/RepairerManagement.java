package org.example.management;

import org.example.model.Repairer;
import org.example.service.RepairerServiceImpl;

import java.util.Scanner;

public class RepairerManagement {
    private final RepairerServiceImpl repairService;
    private final Scanner scanner;

    public RepairerManagement(RepairerServiceImpl repairService, Scanner scanner) {
        this.repairService = repairService;
        this.scanner = scanner;
    }

    public void addRepairer() {
        System.out.println("Enter repairer name.");
        String name = scanner.nextLine();
        repairService.save(name);
        System.out.println("Repairer " + name + " successfully saved");
    }

    public void removeRepairer() {
        System.out.println("Choose the repairer you need to remove from the list bellow.");
        repairService.getAll().stream()
                .map(Repairer::getName)
                .forEach(System.out::println);
        String name = scanner.nextLine();
        repairService.remove(name);
        System.out.println("Repairer " + name + " successfully removed");
    }

    public void getAllRepairersSortedByName() {
        repairService.sortedByName()
                .forEach(r -> System.out.println(r.getName()
                        + ", status: "
                        + r.getStatus()));
    }

    public void getAllRepairersSortedByStatus() {
        repairService.sortedByStatus()
                .forEach(r -> System.out.println("Status: "
                        + r.getStatus()
                        + ", "
                        + r.getName()));
    }
}
