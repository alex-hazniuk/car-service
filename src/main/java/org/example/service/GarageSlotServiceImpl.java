package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.example.repository.GarageSlotRepository;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@RequiredArgsConstructor
public class GarageSlotServiceImpl implements GarageSlotService {

    private final GarageSlotRepository garageSlotRepository;

    private int id;

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
        try {
            return garageSlotRepository.remove(id);
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlotRepository.getAll();
    }

    @Override
    public List<GarageSlot> sortedByStatus() {
        return garageSlotRepository.getAll().stream()
                .sorted(comparing(GarageSlot::getStatus))
                .collect(Collectors.toList());
    }

    @Override
    public GarageSlot changeStatus(int id) {
        GarageSlot garageSlot = garageSlotRepository.findById(id);
        if (garageSlot.getStatus() == GarageSlotStatus.AVAILABLE) {
            garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
        } else {
            garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
        }
        return garageSlot;
    }

    @Override
    public GarageSlot findById(int id) {
        try {
            return garageSlotRepository.findById(id);
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
            return new GarageSlot();
        }
    }
}
