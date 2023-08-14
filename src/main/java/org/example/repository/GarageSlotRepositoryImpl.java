package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.model.GarageSlot;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GarageSlotRepositoryImpl implements GarageSlotRepository {

    private final List<GarageSlot> garageSlots;

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

    @Override
    public boolean delete(GarageSlot garageSlot) {
        return garageSlots.remove(garageSlot);
    }

    @Override
    public GarageSlot update(int index, GarageSlot garageSlot) {
        garageSlots.set(index, garageSlot);
        return garageSlot;
    }
}
