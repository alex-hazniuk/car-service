package org.example.repository;

import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;

import java.util.ArrayList;
import java.util.List;

public class GarageSlotRepositoryImpl implements GarageSlotRepository {

    private static final List<GarageSlot> garageSlots = new ArrayList<>();

    @Override
    public void add(GarageSlot garageSlot) {
        garageSlots.add(garageSlot);
    }

    @Override
    public boolean remove(int id) {
        GarageSlot garageSlot = garageSlots.stream()
                .filter(gs -> gs.getId() == id)
                .findAny().orElseThrow(() ->
                        new InvalidIdException("Can't find garageSlot by id: " + id));
        return garageSlots.remove(garageSlot);
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlots;
    }

    @Override
    public GarageSlot findById(int id) {
        return garageSlots.stream()
                .filter(garageSlot -> garageSlot.getId() == id)
                .findAny().orElseThrow(() ->
                        new InvalidIdException("Can't find garageSlot by id: " + id));
    }
}
