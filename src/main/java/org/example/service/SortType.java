package org.example.service;
import org.example.model.Order;

import java.time.LocalDateTime;
import java.util.Comparator;

public enum SortType {
    PRICE((Comparator.comparing(Order::getPrice))),
    CREATED_AT((Comparator.comparing(Order::getCreatedAt))),
    COMPLETED_AT(((o1, o2) -> o1.getCompletedAt().orElse(LocalDateTime.MIN).compareTo(o2.getCompletedAt().orElse(LocalDateTime.MIN)))),
    STATUS((Comparator.comparing(o -> o.getStatus().name())));

    private final Comparator<Order> comparator;


    SortType(Comparator<Order> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Order> getComparator() {
        return comparator;
    }
}
