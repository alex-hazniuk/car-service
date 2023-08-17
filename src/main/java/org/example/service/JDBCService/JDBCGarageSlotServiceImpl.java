package org.example.service.JDBCService;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.service.GarageSlotService;

import java.util.List;

import static java.util.Comparator.comparing;

@RequiredArgsConstructor
public class JDBCGarageSlotServiceImpl implements GarageSlotService {
    private final GarageSlotJDBCRepository garageSlotJDBCRepository;

    @Override
    public GarageSlot save() {
        GarageSlot garageSlot = GarageSlot.builder()
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        garageSlotJDBCRepository.add(garageSlot);
        return garageSlot;
    }

    @Override
    public boolean remove(int id) {
        GarageSlot garageSlot = findById(id);
        return garageSlotJDBCRepository.delete(garageSlot);
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlotJDBCRepository.getAll();
    }

    @Override
    public List<GarageSlot> sortedByStatus() {
        return garageSlotJDBCRepository.getAll().stream()
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

        return garageSlotJDBCRepository.update(garageSlot);
    }

    @Override
    public GarageSlot findById(int id) {
        return garageSlotJDBCRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Can't find garageSlot by id: " + id));
    }
}

