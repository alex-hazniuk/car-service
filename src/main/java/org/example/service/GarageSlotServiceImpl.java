package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepository;
import java.util.List;
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
        GarageSlot garageSlot = findById(id);
        return getAll().remove(garageSlot);
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
        if (garageSlot.getStatus() == GarageSlotStatus.AVAILABLE) {
            garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
        } else {
            garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
        }
        return garageSlot;
    }

    @Override
    public GarageSlot findById(int id) {
        return garageSlotRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Can't find garageSlot by id: " + id));
    }
}
