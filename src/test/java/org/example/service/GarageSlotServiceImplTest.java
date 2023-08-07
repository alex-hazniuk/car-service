package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GarageSlotServiceImplTest {

    private GarageSlotRepositoryImpl garageSlotRep;
    private GarageSlotService garageSlotService;

    @BeforeEach
    public void setUp() {
        garageSlotRep = new GarageSlotRepositoryImpl();
        garageSlotService = new GarageSlotServiceImpl(garageSlotRep);
    }

    @AfterEach
    public void tearDown() {
        List<GarageSlot> slots = new ArrayList<>(garageSlotRep.getAll());
        for (GarageSlot slot : slots) {
            garageSlotService.remove(slot.getId());
        }
    }

    //  test cases for save() method
    @Test
    void saveSingleGarageSlot() {
        garageSlotService.save();
        List<GarageSlot> savedSlots = garageSlotService.getAll();
        assertEquals(1, savedSlots.size());

        GarageSlot savedGarageSlot = savedSlots.get(0);
        assertNotNull(savedGarageSlot);
        assertEquals(GarageSlotStatus.AVAILABLE, savedGarageSlot.getStatus());
    }

    @Test
    void testSaveMultipleGarageSlots() {
        garageSlotService.save();
        garageSlotService.save();
        List<GarageSlot> savedSlots = garageSlotService.getAll();
        assertEquals(2, savedSlots.size());

        GarageSlot savedGarageSlot1 = savedSlots.get(0);
        GarageSlot savedGarageSlot2 = savedSlots.get(1);
        assertNotNull(savedGarageSlot1);
        assertNotNull(savedGarageSlot2);
        assertNotEquals(savedGarageSlot1.getId(), savedGarageSlot2.getId());
        assertEquals(GarageSlotStatus.AVAILABLE, savedGarageSlot1.getStatus());
        assertEquals(GarageSlotStatus.AVAILABLE, savedGarageSlot2.getStatus());
    }

    //  test cases for remove() method
    @Test
    void testRemoveExistingId() {
        int id1 = 1;
        int id2 = 2;
        garageSlotService.save();
        garageSlotService.save();

        boolean removed = garageSlotService.remove(id2);
        assertTrue(removed);
        List<GarageSlot> remainingSlots = garageSlotService.getAll();
        assertEquals(1, remainingSlots.size());
        assertNotNull(garageSlotService.findById(id1));
        assertThrows(InvalidIdException.class, () -> garageSlotService.remove(id2));
    }

    @Test
    void testRemoveNonExistingId() {
        int id = 12;
        assertThrows(InvalidIdException.class, () -> garageSlotService.remove(id));
    }


    //  test cases for getAll() method
    @Test
    void getAllEmptyList() {
        List<GarageSlot> result = garageSlotService.getAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllWithMultipleSlots() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        GarageSlot garageSlot3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotRep.add(garageSlot1);
        garageSlotRep.add(garageSlot2);
        garageSlotRep.add(garageSlot3);

        List<GarageSlot> results = garageSlotService.getAll();

        assertEquals(3, results.size());
        assertEquals(garageSlot1, results.get(0));
        assertEquals(garageSlot2, results.get(1));
        assertEquals(garageSlot3, results.get(2));
    }

    @Test
    void getAllAfterRemovingSlots() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(garageSlot1);
        garageSlotRep.add(garageSlot2);

        garageSlotService.remove(garageSlot1.getId()); // Removing garageSlot1

        List<GarageSlot> result = garageSlotService.getAll();

        assertEquals(1, result.size());
        assertEquals(garageSlot2, result.get(0));
    }

    //  test cases for sortedByStatus() method
    @Test
    void sortedByStatusWithSingleSlot() {
        GarageSlot g1 = new GarageSlot(6, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(g1);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertEquals(Collections.singletonList(g1), sortedSlots);
    }

    @Test
    void sortedByStatusWithMultipleSlotsSameStatus() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotRep.add(g1);
        garageSlotRep.add(g2);
        garageSlotRep.add(g3);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertEquals(Arrays.asList(g1, g2, g3), sortedSlots);
    }

    @Test
    void sortedByStatusWithMixedStatusSlots() {
        GarageSlot g1 = new GarageSlot(11, GarageSlotStatus.UNAVAILABLE);
        GarageSlot g2 = new GarageSlot(12, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(13, GarageSlotStatus.AVAILABLE);
        GarageSlot g4 = new GarageSlot(14, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(g1);
        garageSlotRep.add(g2);
        garageSlotRep.add(g3);
        garageSlotRep.add(g4);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertEquals(Arrays.asList(g2, g3, g1, g4), sortedSlots);
    }

    //  test cases for changeStatus() method
    @Test
    void testChangeStatusFromAvailableToUnavailable() {
        int id = 1;
        GarageSlot initialGarageSlot = GarageSlot.builder()
                .id(id)
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        garageSlotRep.add(initialGarageSlot);

        GarageSlot updatedGarageSlot = garageSlotService.changeStatus(id);
        assertEquals(GarageSlotStatus.UNAVAILABLE, updatedGarageSlot.getStatus());
    }


    @Test
    void testChangeStatusFromUnavailableToAvailable() {
        int id = 2;
        GarageSlot initialGarageSlot = GarageSlot.builder()
                .id(id)
                .status(GarageSlotStatus.UNAVAILABLE)
                .build();
        garageSlotRep.add(initialGarageSlot);

        GarageSlot updatedGarageSlot = garageSlotService.changeStatus(id);
        assertEquals(GarageSlotStatus.AVAILABLE, updatedGarageSlot.getStatus());
    }


    //  test cases for findById() method
    @Test
    void findByIdExistingSlot() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        garageSlotRep.add(g1);

        GarageSlot foundSlot = garageSlotService.findById(1);

        assertNotNull(foundSlot);
        assertEquals(g1, foundSlot);
    }

    @Test
    void findByIdNonExistingSlot() {
        assertThrows(InvalidIdException.class, () -> garageSlotService.findById(100));
    }

    @Test
    void findByIdWithMultipleSlots() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.UNAVAILABLE);
        GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(g1);
        garageSlotRep.add(g2);
        garageSlotRep.add(g3);

        GarageSlot foundSlot1 = garageSlotService.findById(2);
        GarageSlot foundSlot3 = garageSlotService.findById(3);

        assertNotNull(foundSlot1);
        assertEquals(g2, foundSlot1);
        assertEquals(g3.getId(), foundSlot3.getId());
        assertEquals(g3.getStatus(), foundSlot3.getStatus());
    }

    @Test
    void testFindByIdNonExistingSlotWithExceptionMessage() {
        int nonExistingId = 999;

        Exception exception = assertThrows(InvalidIdException.class, () -> {
            garageSlotService.findById(nonExistingId);
        });

        String expectedMessage = "Can't find garageSlot by id: " + nonExistingId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
