package org.example.InMemoryRepositories;

import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.InMemoryRepositories.GarageSlotInMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GarageSlotInMemoryRepositoryTest {

    private final List<GarageSlot> garageSlots = new ArrayList<>();
    private final GarageSlotInMemoryRepository garageSlotInMemoryRepository = new GarageSlotInMemoryRepository(garageSlots);


// add()
    @Test
    void whenAddingSingleSlot_thenOneSlotMustBeAdded() {
        GarageSlot garageSlot = new GarageSlot();
        garageSlotInMemoryRepository.add(garageSlot);
        List<GarageSlot> addedSlots = garageSlotInMemoryRepository.getAll();
        assertThat(addedSlots).hasSize(1);

        GarageSlot savedGarageSlot = addedSlots.get(0);
        assertThat(savedGarageSlot).isNotNull();
        assertThat(savedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE);
    }

    @Test
    void whenAddingMultipleSlots_thenAllOfTHemMustBeAdded() {
        GarageSlot garageSlot1 = new GarageSlot();
        GarageSlot garageSlot2 = new GarageSlot();

        garageSlotInMemoryRepository.add(garageSlot1);
        garageSlotInMemoryRepository.add(garageSlot2);
        List<GarageSlot> addedSlots = garageSlotInMemoryRepository.getAll();
        assertThat(addedSlots).hasSize(2);
        assertThat(addedSlots).allSatisfy(slot ->
                assertThat(slot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE)
        );
        assertThat(addedSlots).containsExactly(garageSlot1, garageSlot2);
    }

// getAll()
    @Test
    void whenGettingAllGarageSlotsWithEmptyList_thenListShouldBeEmpty() {
        List<GarageSlot> result = garageSlotInMemoryRepository.getAll();
        assertThat(result).isEmpty();
    }

    @Test
    void whenGettingAllGarageSlotsWithMultipleSlots_thenAllSlotsShouldBeReturned() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        GarageSlot garageSlot3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotInMemoryRepository.add(garageSlot1);
        garageSlotInMemoryRepository.add(garageSlot2);
        garageSlotInMemoryRepository.add(garageSlot3);

        List<GarageSlot> results = garageSlotInMemoryRepository.getAll();

        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(garageSlot1, garageSlot2, garageSlot3);
    }

    @Test
    void whenGettingAllGarageSlotsAfterRemovingSlots_thenRemainingSlotsShouldBeReturned() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        garageSlotInMemoryRepository.add(garageSlot1);
        garageSlotInMemoryRepository.add(garageSlot2);

        garageSlotInMemoryRepository.delete(garageSlot1);
        List<GarageSlot> result = garageSlotInMemoryRepository.getAll();

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(garageSlot2);
    }

// getAllSortedByStatus()
    @Test
    void whenSortingSlotsByStatusWithSingleSlot_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(6, GarageSlotStatus.UNAVAILABLE);
        garageSlotInMemoryRepository.add(g1);

        List<GarageSlot> sortedSlots = garageSlotInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedSlots).containsExactly(g1);
        assertThat(g1.getStatus()).isEqualTo(GarageSlotStatus.UNAVAILABLE);
    }

    @Test
    void whenSortingSlotsByStatusWithMultipleSlotsSameStatus_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotInMemoryRepository.add(g1);
        garageSlotInMemoryRepository.add(g2);
        garageSlotInMemoryRepository.add(g3);

        List<GarageSlot> sortedSlots = garageSlotInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedSlots).containsExactly(g1, g2, g3);
    }

    @Test
    void whenSortingSlotsByStatusWithMixedStatusSlots_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(11, GarageSlotStatus.UNAVAILABLE);
        GarageSlot g2 = new GarageSlot(12, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(13, GarageSlotStatus.AVAILABLE);
        GarageSlot g4 = new GarageSlot(14, GarageSlotStatus.UNAVAILABLE);
        garageSlotInMemoryRepository.add(g1);
        garageSlotInMemoryRepository.add(g2);
        garageSlotInMemoryRepository.add(g3);
        garageSlotInMemoryRepository.add(g4);

        List<GarageSlot> sortedSlots = garageSlotInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedSlots).containsExactly(g2, g3, g1, g4);
    }

// findById() {
@Test
void whenFindingExistingSlotById_thenSlotShouldBeFound() {
    GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
    garageSlotInMemoryRepository.add(g1);

    Optional<GarageSlot> foundSlot = garageSlotInMemoryRepository.findById(g1.getId());
    assertThat(foundSlot).isPresent();
    assertThat(foundSlot.get()).isEqualTo(g1);
}

    @Test
    void whenFindingNonExistingSlotById_thenMustReturnEmpty() {
        int nonExistingId = 999;
        Optional<GarageSlot> notFoundSlot = garageSlotInMemoryRepository.findById(nonExistingId);
        assertThat(notFoundSlot.isEmpty());
    }

// delete()
@Test
void whenDeletingExistingSlot_thenSlotShouldBeDeletedAndNotFound() {
    GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
    GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
    GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
    garageSlotInMemoryRepository.add(g1);
    garageSlotInMemoryRepository.add(g2);
    garageSlotInMemoryRepository.add(g3);


    boolean toBeDeleted = garageSlotInMemoryRepository.delete(g1);
    assertThat(toBeDeleted).isTrue();


    List<GarageSlot> remainingSlots = garageSlotInMemoryRepository.getAll();
    assertThat(remainingSlots).hasSize(2);
    assertThat(garageSlotInMemoryRepository.findById(g2.getId())).isNotNull();
    assertThat(garageSlotInMemoryRepository.findById(g3.getId())).isNotNull();
}

    @Test
    void whenRemovingNonExistingSlot_thenInvalidIdExceptionShouldBeThrown() {
        GarageSlot g1 = new GarageSlot();
        boolean deleted = garageSlotInMemoryRepository.delete(g1);
        assertThat(deleted).isFalse();
    }

// update()
    @Test
    void whenUpdatingExistingSlot_thenSlotMustBeUpdated() {
        GarageSlot oldGarageSlot = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        garageSlotInMemoryRepository.add(oldGarageSlot);
        GarageSlot newGarageSlot = new GarageSlot(1, GarageSlotStatus.UNAVAILABLE);
        GarageSlot updatedGarageSlot = garageSlotInMemoryRepository.update(newGarageSlot);
        assertThat(updatedGarageSlot).isEqualTo(newGarageSlot);
        assertThat(garageSlotInMemoryRepository.getAll()).contains(updatedGarageSlot);
    }

    @Test
    void whenUpdatingNonExistingSlot_thenExceptionMustBeThrown(){
        GarageSlot garageSlot = new GarageSlot(1, GarageSlotStatus.UNAVAILABLE);
        assertThatThrownBy(() -> garageSlotInMemoryRepository.update(garageSlot))
                .isInstanceOf(NoSuchElementException.class);

    }

}