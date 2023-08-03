package org.example.repository;

import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;

import java.util.ArrayList;
import java.util.List;

public class GarageSlotRepositoryImpl implements GarageSlotRepository {

    public static final String NO_SUCH_ID = "No such garage id:";

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
                .orElseThrow(() -> new InvalidIdException(NO_SUCH_ID + id));

        garageSlots.remove(garage);

        return false;
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlots;
    }

    @Override
    public GarageSlot findById(int id) {
        return garageSlots.stream()
                .filter(garageSlot -> garageSlot.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidIdException(NO_SUCH_ID + id));

    }

}
