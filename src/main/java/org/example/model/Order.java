package org.example.model;

import org.example.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class Order {

    private Long id;
    private Set<Repairer> repairers;
    private GarageSlot garageSlot;
    private Double price;
    private LocalDateTime createdAt;
    private Optional<LocalDateTime> completedAt;
    private OrderStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Repairer> getRepairers() {
        return repairers;
    }

    public void setRepairers(Set<Repairer> repairers) {
        this.repairers = repairers;
    }

    public GarageSlot getGarageSlot() {
        return garageSlot;
    }

    public void setGarageSlot(GarageSlot garageSlot) {
        this.garageSlot = garageSlot;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<LocalDateTime> getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Optional<LocalDateTime> completedAt) {
        this.completedAt = completedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
