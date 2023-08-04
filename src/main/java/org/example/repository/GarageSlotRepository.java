package org.example.repository;

import org.example.model.GarageSlot;

import java.util.List;
import java.util.Optional;

public interface GarageSlotRepository {

    void add(GarageSlot garageSlot);

    Optional<Boolean> remove(int id);

    List<GarageSlot> getAll();

    Optional<GarageSlot> findById(int id);

}

