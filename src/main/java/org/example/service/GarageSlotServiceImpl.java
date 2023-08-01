package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.GarageSlotDaoImpl;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;

import java.util.List;

import static java.util.Comparator.comparing;
@AllArgsConstructor
public class GarageSlotServiceImpl implements GarageSlotService {
    private final GarageSlotDaoImpl garageSlotDao;


    @Override
    public void save(int id) {
        GarageSlot garageSlot = GarageSlot.builder()
                .id(id)
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        garageSlotDao.add(garageSlot);
    }

    @Override
    public boolean remove(int id) {
        return garageSlotDao.remove(id);
    }

    @Override
    public List<GarageSlot> getAll() {
        return garageSlotDao.getAll();
    }

    @Override
    public List<GarageSlot> sortedByStatus() {
        return garageSlotDao.getAll().stream()
                .sorted(comparing(GarageSlot::getStatus))
                .toList();
    }

    @Override
    public GarageSlot changeStatus(GarageSlot garageSlot) {
        if (garageSlot.getStatus() == GarageSlotStatus.AVAILABLE) {
            garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
        } else {
            garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
        }
        return garageSlot;
    }


}
