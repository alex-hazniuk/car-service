package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GarageSlotServiceImplTest {

    private GarageSlotRepositoryImpl garageSlotRep = new GarageSlotRepositoryImpl(new ArrayList<>());
    private GarageSlotService garageSlotService = new GarageSlotServiceImpl(garageSlotRep);

    @Test
    void whenSavingSingleGarageSlot_thenSlotShouldBeSavedWithAvailableStatus() {
        garageSlotService.save();
        List<GarageSlot> savedSlots = garageSlotService.getAll();
        assertThat(savedSlots).hasSize(1);

        GarageSlot savedGarageSlot = savedSlots.get(0);
        assertThat(savedGarageSlot).isNotNull();
        assertThat(savedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE);
    }

    @Test
    void whenSavingMultipleGarageSlots_thenAllSlotsShouldBeSavedWithAvailableStatus() {
        garageSlotService.save();
        garageSlotService.save();
        List<GarageSlot> savedSlots = garageSlotService.getAll();
        assertThat(savedSlots).hasSize(2);

        assertThat(savedSlots).allSatisfy(slot ->
                assertThat(slot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE)
        );
    }

    @Test
    void whenRemovingExistingSlot_thenSlotShouldBeRemovedAndNotFound() {
        int id1 = 1;
        int id2 = 2;
        garageSlotService.save();
        garageSlotService.save();

        boolean removed = garageSlotService.remove(id2);
        assertTrue(removed);

        assertThatThrownBy(() -> garageSlotService.remove(id2))
                .isInstanceOf(InvalidIdException.class);

        List<GarageSlot> remainingSlots = garageSlotService.getAll();
        assertThat(remainingSlots).hasSize(1);

        assertThat(garageSlotService.findById(id1)).isNotNull();
    }

    @Test
    void whenRemovingNonExistingSlot_thenInvalidIdExceptionShouldBeThrown() {
        int nonExistingId = 12;
        assertThatThrownBy(() -> garageSlotService.remove(nonExistingId))
                .isInstanceOf(InvalidIdException.class);
    }

    @Test
    void whenGettingAllGarageSlotsWithEmptyList_thenListShouldBeEmpty() {
        List<GarageSlot> result = garageSlotService.getAll();
        assertThat(result).isEmpty();
    }

    @Test
    void whenGettingAllGarageSlotsWithMultipleSlots_thenAllSlotsShouldBeReturned() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        GarageSlot garageSlot3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotRep.add(garageSlot1);
        garageSlotRep.add(garageSlot2);
        garageSlotRep.add(garageSlot3);

        List<GarageSlot> results = garageSlotService.getAll();
        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(garageSlot1, garageSlot2, garageSlot3);
    }

    @Test
    void whenGettingAllGarageSlotsAfterRemovingSlots_thenRemainingSlotsShouldBeReturned() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(garageSlot1);
        garageSlotRep.add(garageSlot2);

        garageSlotService.remove(garageSlot1.getId());
        List<GarageSlot> result = garageSlotService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(garageSlot2);
    }

    @Test
    void whenSortingSlotsByStatusWithSingleSlot_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(6, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(g1);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertThat(sortedSlots).containsExactly(g1);
    }

    @Test
    void whenSortingSlotsByStatusWithMultipleSlotsSameStatus_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotRep.add(g1);
        garageSlotRep.add(g2);
        garageSlotRep.add(g3);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertThat(sortedSlots).containsExactly(g1, g2, g3);
    }

    @Test
    void whenSortingSlotsByStatusWithMixedStatusSlots_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(11, GarageSlotStatus.UNAVAILABLE);
        GarageSlot g2 = new GarageSlot(12, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(13, GarageSlotStatus.AVAILABLE);
        GarageSlot g4 = new GarageSlot(14, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(g1);
        garageSlotRep.add(g2);
        garageSlotRep.add(g3);
        garageSlotRep.add(g4);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertThat(sortedSlots).containsExactly(g2, g3, g1, g4);
    }

    @Test
    void whenChangingStatusFromAvailableToUnavailable_thenStatusShouldBeUpdated() {
        int id = 1;
        GarageSlot initialGarageSlot = GarageSlot.builder()
                .id(id)
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        garageSlotRep.add(initialGarageSlot);

        GarageSlot updatedGarageSlot = garageSlotService.changeStatus(id);
        assertThat(updatedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.UNAVAILABLE);
    }

    @Test
    void whenChangingStatusFromUnavailableToAvailable_thenStatusShouldBeUpdated() {
        int id = 2;
        GarageSlot initialGarageSlot = GarageSlot.builder()
                .id(id)
                .status(GarageSlotStatus.UNAVAILABLE)
                .build();
        garageSlotRep.add(initialGarageSlot);

        GarageSlot updatedGarageSlot = garageSlotService.changeStatus(id);
        assertThat(updatedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE);
    }

    @Test
    void whenFindingExistingSlotById_thenSlotShouldBeFound() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        garageSlotRep.add(g1);

        GarageSlot foundSlot = garageSlotService.findById(1);
        assertThat(foundSlot).isEqualTo(g1);
    }

    @Test
    void whenFindingNonExistingSlotById_thenInvalidIdExceptionShouldBeThrownWithMessage() {
        int nonExistingId = 999;

        assertThatThrownBy(() -> garageSlotService.findById(nonExistingId))
                .isInstanceOf(InvalidIdException.class)
                .hasMessageContaining("Can't find garageSlot by id: " + nonExistingId);
    }
}
