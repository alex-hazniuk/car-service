package org.example.repository;

import org.example.model.GarageSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GarageSlotRepositoryImpl implements GarageSlotRepository {

    private static final List<GarageSlot> garageSlots = new ArrayList<>();

    @Override
    public void add(GarageSlot garageSlot) {
        garageSlots.add(garageSlot);
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlots;
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        return garageSlots.stream()
                .filter(garageSlot -> garageSlot.getId() == id)
                .findAny();
    }
}
