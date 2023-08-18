package org.example.repository;

import org.example.model.GarageSlot;

import java.util.List;
import java.util.Optional;


public interface GarageSlotRepository {

    GarageSlot add(GarageSlot garageSlot);

    List<GarageSlot> getAll();

    List<GarageSlot> getAllSortedByStatus();

    Optional<GarageSlot> findById(int id);

    boolean delete(GarageSlot garageSlot);

    GarageSlot update(GarageSlot garageSlot);




}

