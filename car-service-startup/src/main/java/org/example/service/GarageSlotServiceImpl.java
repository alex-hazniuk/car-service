package org.example.service;

import org.example.GarageSlotRepository;
import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageSlotServiceImpl implements GarageSlotService {

    private final GarageSlotRepository garageSlotRepository;

    public GarageSlotServiceImpl(GarageSlotRepository garageSlotRepository) {
        this.garageSlotRepository = garageSlotRepository;
    }

    @Override
    public GarageSlot save() {
        GarageSlot garageSlot = GarageSlot.builder()
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        return garageSlotRepository.add(garageSlot);
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
        return garageSlotRepository.getAllSortedByStatus();
    }

    @Override
    public GarageSlot changeStatus(int id) {
        GarageSlot garageSlot = findById(id);

        if (garageSlot.getStatus() == GarageSlotStatus.AVAILABLE) {
            garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
        } else {
            garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
        }

        return garageSlotRepository.update(garageSlot);
    }

    @Override
    public GarageSlot findById(int id) {
        return garageSlotRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Can't find garageSlot by id: " + id));
    }
}
