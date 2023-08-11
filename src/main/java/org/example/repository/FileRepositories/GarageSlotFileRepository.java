package org.example.repository.FileRepositories;

import org.example.model.GarageSlot;
import org.example.repository.GarageSlotRepository;

import java.util.List;
import java.util.Optional;

public class GarageSlotFileRepository implements GarageSlotRepository {

    private final CarServiceStoreHandler carServiceStoreHandler;

    public GarageSlotFileRepository(CarServiceStoreHandler carServiceSerialization) {
        this.carServiceStoreHandler = carServiceSerialization;
    }

    @Override
    public void add(GarageSlot garageSlot) {
        State state = carServiceStoreHandler.read();
        List<GarageSlot> garageSlots = state.garageSlots();
        garageSlots.add(garageSlot);
        carServiceStoreHandler.write(state.withGarageSlots(garageSlots));
    }

    @Override
    public List<GarageSlot> getAll() {
        return carServiceStoreHandler.read().garageSlots();
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        return getAll().stream().filter(garageSlot -> garageSlot.getId() == id).findAny();
    }
}
