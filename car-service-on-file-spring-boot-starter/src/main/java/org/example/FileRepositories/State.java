package org.example.FileRepositories;

import org.example.model.GarageSlot;
import org.example.model.Order;
import org.example.model.Repairer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

record State(
        List<GarageSlot> garageSlots,

        List<Repairer> repairers,

        Map<Long, Order> orders

) {
    State() {
        this(new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }

    State withGarageSlots(List<GarageSlot> garageSlots) {
        return new State(garageSlots, repairers, orders);
    }

    State withRepairers(List<Repairer> repairers) {
        return new State(garageSlots, repairers, orders);
    }

    State withOrders(Map<Long, Order> orders) {
        return new State(garageSlots, repairers, orders);
    }

}


