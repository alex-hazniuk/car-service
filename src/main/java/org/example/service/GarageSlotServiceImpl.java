package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepository;

import java.util.List;

import static java.util.Comparator.comparing;

public class GarageSlotServiceImpl implements GarageSlotService {

    private final GarageSlotRepository garageSlotRepository;
    private int id;

    public GarageSlotServiceImpl(GarageSlotRepository garageSlotRepository) {
        this.garageSlotRepository = garageSlotRepository;
        this.id = garageSlotRepository.getAll().size();
    }

    @Override
    public void save() {
        GarageSlot garageSlot = GarageSlot.builder()
                .id(++id)
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        garageSlotRepository.add(garageSlot);
    }

    @Override
    public boolean remove(int id) {
        GarageSlot garageSlot = findById(id);
        return garageSlotRepository.delete(garageSlot);

    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlotRepository.getAll();
    }

    @Override
    public List<GarageSlot> sortedByStatus() {
        return garageSlotRepository.getAll().stream()
                .sorted(comparing(GarageSlot::getStatus))
                .toList();
    }

    @Override
    public GarageSlot changeStatus(int id) {
        GarageSlot garageSlot = findById(id);
        int index = getAll().indexOf(garageSlot);

        if (garageSlot.getStatus() == GarageSlotStatus.AVAILABLE) {
            garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
        } else {
            garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
        }

        return garageSlotRepository.update(index ,garageSlot);
    }

    @Override
    public GarageSlot findById(int id) {
        return garageSlotRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Can't find garageSlot by id: " + id));
    }
}
