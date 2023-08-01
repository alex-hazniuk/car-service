package org.example;

import org.example.dao.RepairerDaoImpl;
import org.example.management.ManagementCommand;
import org.example.management.RepairerManagement;
import org.example.service.RepairerServiceImpl;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                """
                        Glad to see you in our car service!
                        Choose the command bellow by number:
                        """
        );

        Arrays.stream(ManagementCommand.values())
                .forEach(mc -> System.out.println(mc + " " + mc.getTitle()));
        run(scanner);
    }

    private static void run(Scanner scanner) {
        RepairerDaoImpl repairerDao = new RepairerDaoImpl();
        RepairerManagement repairerManagement =
                new RepairerManagement(new RepairerServiceImpl(repairerDao), scanner);
        int command = scanner.nextInt();
        while (command >= 1 && command <= 4) {
            scanner.nextLine();
            switch (command) {
                case 1 -> repairerManagement.addRepairer();
                case 2 -> repairerManagement.removeRepairer();
                case 3 -> repairerManagement.getAllRepairersSortedByName();
                case 4 -> repairerManagement.getAllRepairersSortedByStatus();
            }
            System.out.println(
                    """
                            In case you want out, enter any number not from the list!
                            Choose the command bellow by number:
                            """
            );
            Arrays.stream(ManagementCommand.values())
                    .forEach(mc -> System.out.println(mc + " " + mc.getTitle()));
            command = scanner.nextInt();
        }
    }
}
