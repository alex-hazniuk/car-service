package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepository;
import org.example.repository.GarageSlotRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;


public class GarageSlotServiceImpl implements GarageSlotService {

    private static GarageSlotService INSTANCE;

    private final GarageSlotRepository garageSlotRepository;

    private int id = 0;

    private GarageSlotServiceImpl() {
        this.garageSlotRepository = GarageSlotRepositoryImpl.getINSTANCE();
    }



    public static GarageSlotService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GarageSlotServiceImpl();
        }
        return INSTANCE;
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
        try {
            return garageSlotRepository.remove(id).get();
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
    public GarageSlot changeStatus(GarageSlot garageSlot) {
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
            return garageSlotRepository.findById(id).get();
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
            return new GarageSlot();
        }
    }
}
