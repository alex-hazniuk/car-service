package org.example.repository;

import org.example.model.GarageSlot;

import java.util.List;

public interface GarageSlotRepository {

    void add(GarageSlot garageSlot);

    boolean remove(int id);

    List<GarageSlot> getAll();

    GarageSlot findById(int id);

}

