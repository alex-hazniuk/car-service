package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Set<Repairer> repairers;
    private GarageSlot garageSlot;
    private Double price;
    private LocalDateTime createdAt;
    private Optional<LocalDateTime> completedAt;
    private OrderStatus status;
}
