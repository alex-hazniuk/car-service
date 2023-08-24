package org.example.FileRepositories;

import org.example.GarageSlotRepository;
import org.example.model.GarageSlot;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

public class GarageSlotFileRepository implements GarageSlotRepository {

    private final CarServiceStoreHandler carServiceStoreHandler;

    private int id;

    public GarageSlotFileRepository(CarServiceStoreHandler carServiceSerialization) {
        this.carServiceStoreHandler = carServiceSerialization;
        this.id = getAll().size();
    }

    @Override
    public GarageSlot add(GarageSlot garageSlot) {
        State state = carServiceStoreHandler.read();
        List<GarageSlot> garageSlots = state.garageSlots();
        garageSlot.setId(++id);
        garageSlots.add(garageSlot);
        carServiceStoreHandler.write(state.withGarageSlots(garageSlots));
        return garageSlot;
    }

    @Override
    public List<GarageSlot> getAll() {
        return carServiceStoreHandler.read().garageSlots();
    }

    @Override
    public List<GarageSlot> getAllSortedByStatus() {
        return carServiceStoreHandler.read().garageSlots().stream()
                .sorted(comparing(GarageSlot::getStatus))
                .toList();
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        return getAll().stream().filter(garageSlot -> garageSlot.getId() == id).findAny();
    }

    @Override
    public boolean delete(GarageSlot garageSlot) {
        State state = carServiceStoreHandler.read();
        List<GarageSlot> garageSlots = state.garageSlots();
        boolean delete = garageSlots.remove(garageSlot);
        carServiceStoreHandler.write(state.withGarageSlots(garageSlots));

        return delete;
    }

    @Override
    public GarageSlot update(GarageSlot garageSlot) {
        State state = carServiceStoreHandler.read();
        List<GarageSlot> garageSlots = state.garageSlots();
        garageSlots.set(garageSlots.indexOf(garageSlot), garageSlot);
        carServiceStoreHandler.write(state.withGarageSlots(garageSlots));

        return garageSlot;
    }
}
