package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

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
        Properties property = readProperties();
        if (property.getProperty("Ability-To-Add").equals("on")) {
            GarageSlot garageSlot = GarageSlot.builder()
                    .id(++id)
                    .status(GarageSlotStatus.AVAILABLE)
                    .build();
            garageSlotRepository.add(garageSlot);
        } else {
            System.out.println("Ability to add is not available");
        }
    }

    @Override
    public boolean remove(int id) {
        GarageSlot garageSlot = findById(id);
        Properties property = readProperties();
        if (property.getProperty("Ability-To-Delete").equals("on")) {
            return getAll().remove(garageSlot);
        } else {
            System.out.println("Ability to delete is not available");
        }
        return true;
    }

    private Properties readProperties() {
        Properties property = new Properties();
        try {
            property.load(new FileInputStream(
                    "src/main/resources/garageSlot.properties"));
        } catch (IOException e) {
            System.err.println("This property file is absent");
        }
        return property;
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
