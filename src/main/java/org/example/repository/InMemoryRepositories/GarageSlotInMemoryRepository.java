package org.example.repository.InMemoryRepositories;

import lombok.AllArgsConstructor;
import org.example.model.GarageSlot;
import org.example.repository.GarageSlotRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@AllArgsConstructor
public class GarageSlotInMemoryRepository implements GarageSlotRepository {

    private final List<GarageSlot> garageSlots;

    private int id;

    @Override
    public GarageSlot add(GarageSlot garageSlot) {
        garageSlot.setId(++id);
        garageSlots.add(garageSlot);
        return garageSlot;
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlots;
    }

    @Override
    public List<GarageSlot> getAllSortedByStatus() {
        return garageSlots.stream()
                .sorted(comparing(GarageSlot::getStatus))
                .toList();
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
    public GarageSlot update(GarageSlot garageSlot) {
        garageSlots.set(garageSlots.indexOf(garageSlot), garageSlot);
        return garageSlot;
    }
}
