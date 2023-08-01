package org.example.management;

public enum ManagementCommand {
    ADD_REPAIRER(1),
    REMOVE_REPAIRER(2),
    SORT_REPAIRERS_BY_NAME(3),
    SORT_REPAIRERS_BY_STATUS(4);

    private final int number;

    ManagementCommand(int number) {
        this.number = number;
    }

    public int getTitle() {
        return number;
    }
}
