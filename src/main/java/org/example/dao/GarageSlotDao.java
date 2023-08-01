package org.example.dao;

import org.example.model.GarageSlot;

import java.util.List;

public interface GarageSlotDao {

    void add(GarageSlot garageSlot);

    boolean remove(int garageSlotId);

    List<GarageSlot> getAll();

}

