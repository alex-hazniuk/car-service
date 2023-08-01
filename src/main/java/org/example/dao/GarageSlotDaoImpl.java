package org.example.dao;

import org.example.model.GarageSlot;

import java.util.ArrayList;
import java.util.List;

public class GarageSlotDaoImpl implements GarageSlotDao {

    private static final List<GarageSlot> garageSlots = new ArrayList<>();

    @Override
    public void add(GarageSlot garageSlot) {
        garageSlots.add(garageSlot);
    }

    @Override
    public boolean remove(int id) {
        var garage = garageSlots.stream()
                .filter(garageSlot -> garageSlot.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such garage id:" + id));

        garageSlots.remove(garage);

        return false;
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlots;
    }



}
