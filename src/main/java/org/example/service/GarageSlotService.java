package org.example.service;


import org.example.model.GarageSlot;

import java.util.List;

public interface GarageSlotService {

    void save();

    boolean remove(int id);

    List<GarageSlot> getAll();

    List<GarageSlot> sortedByStatus();

    GarageSlot changeStatus(int id);

    GarageSlot findById(int id);
}
