package org.example.service;
import org.example.model.Order;

import java.time.LocalDateTime;
import java.util.Comparator;

public enum SortType {
    PRICE((Comparator.comparing(Order::getPrice))),
    CREATED_AT((Comparator.comparing(Order::getCreatedAt))),
    COMPLETED_AT((Comparator.comparing(o -> o.getCompletedAt().orElse(LocalDateTime.MIN)))),
    STATUS((Comparator.comparing(o -> o.getStatus().name())));

    private final Comparator<Order> comparator;


    SortType(Comparator<Order> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Order> getComparator() {
        return comparator;
    }
}
