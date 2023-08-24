package org.example.InMemoryRepositories;

import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.InMemoryRepositories.RepairerInMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepairerInMemoryRepositoryTest {

    private final List<Repairer> repairers = new ArrayList<>();
    private final RepairerInMemoryRepository repairerInMemoryRepository = new RepairerInMemoryRepository(repairers);


    // add()
    @Test
    void whenAddingSingleRepairer_thenOneRepairerMustBeAdded() {
        Repairer repairer = new Repairer(1, "Maxim", RepairerStatus.AVAILABLE);
        repairerInMemoryRepository.add(repairer);
        List<Repairer> addedRepairers = repairerInMemoryRepository.getAll();
        assertThat(addedRepairers).hasSize(1);

        Repairer savedRepairer = addedRepairers.get(0);
        assertThat(savedRepairer).isNotNull();
        assertThat(savedRepairer.getStatus()).isEqualTo(RepairerStatus.AVAILABLE);
    }

    @Test
    void whenAddingMultipleSlots_thenAllOfTHemMustBeAdded() {
        Repairer repairer1 = new Repairer();
        Repairer repairer2 = new Repairer();

        repairerInMemoryRepository.add(repairer1);
        repairerInMemoryRepository.add(repairer2);
        List<Repairer> addedRepairers = repairerInMemoryRepository.getAll();
        assertThat(addedRepairers).hasSize(2);
        assertThat(addedRepairers).containsExactly(repairer1, repairer2);
    }


    @Test
    void whenFindingExistingRepairerById_thenRepairerShouldBeFound() {
        Repairer r1 = new Repairer(1,"Upol", RepairerStatus.BUSY);
        repairerInMemoryRepository.add(r1);

        Optional<Repairer> foundRepairer = repairerInMemoryRepository.findById(r1.getId());
        assertThat(foundRepairer).isPresent();
        assertThat(foundRepairer.get()).isEqualTo(r1);
        assertThat(foundRepairer.get().getName()).isEqualTo("Upol");
        assertThat(foundRepairer.get().getStatus()).isEqualTo(RepairerStatus.BUSY);
    }

    @Test
    void whenFindingNonExistingRepairerById_thenMustReturnEmpty() {
        int nonExistingId = 999;
        Optional<Repairer> notFoundedRepairer = repairerInMemoryRepository.findById(nonExistingId);
        assertThat(notFoundedRepairer.isEmpty());
    }

    @Test
    void whenGettingAllRepairersWithEmptyList_thenListShouldBeEmpty() {
        List<Repairer> result = repairerInMemoryRepository.getAll();
        assertThat(result).isEmpty();
    }

    @Test
    void whenGettingAllRepairersWithMultipleSlots_thenAllRepairersShouldBeReturned() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Alex", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Evelina", RepairerStatus.BUSY);
        Repairer r4 = new Repairer(4,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(5,"Anton", RepairerStatus.BUSY);

        repairerInMemoryRepository.add(r1);
        repairerInMemoryRepository.add(r2);
        repairerInMemoryRepository.add(r3);
        repairerInMemoryRepository.add(r4);
        repairerInMemoryRepository.add(r5);

        List<Repairer> results = repairerInMemoryRepository.getAll();

        assertThat(results).hasSize(5);
        assertThat(results).containsExactly(r1, r2, r3, r4, r5);
    }

    @Test
    void whenSortingRepairerByStatus_thenRepairerShouldBeReturned() {
        Repairer r1 = new Repairer(6, "Upol", RepairerStatus.AVAILABLE);
        repairerInMemoryRepository.add(r1);

        List<Repairer> sortedRepairers = repairerInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1);
        assertThat(r1.getStatus()).isEqualTo(RepairerStatus.AVAILABLE);
    }

    @Test
    void whenSortingMultipleRepairersByStatusWithSameStatus_thenRepairersShouldBeSortedByStatus() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Alex", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Evelina", RepairerStatus.AVAILABLE);
        Repairer r4 = new Repairer(4,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(5,"Anton", RepairerStatus.AVAILABLE);

        repairerInMemoryRepository.add(r1);
        repairerInMemoryRepository.add(r2);
        repairerInMemoryRepository.add(r3);
        repairerInMemoryRepository.add(r4);
        repairerInMemoryRepository.add(r5);

        List<Repairer> sortedRepairers = repairerInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1, r2, r3, r4, r5);
    }

    @Test
    void whenSortingRepairersByStatusWithMixedStatuses_thenRepairersShouldBeSortedByStatus() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Alex", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Evelina", RepairerStatus.BUSY);
        Repairer r4 = new Repairer(4,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(1,"Anton", RepairerStatus.BUSY);

        repairerInMemoryRepository.add(r1);
        repairerInMemoryRepository.add(r2);
        repairerInMemoryRepository.add(r3);
        repairerInMemoryRepository.add(r4);
        repairerInMemoryRepository.add(r5);

        List<Repairer> sortedRepairers= repairerInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1, r2, r4, r3, r5);
    }

    @Test
    void whenSortingMultipleRepairersByNamesWithSameNames_thenRepairersShouldBeSortedByNames() {
        Repairer r1 = new Repairer(1,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Sergei", RepairerStatus.BUSY);
        Repairer r3 = new Repairer(3,"Sergei", RepairerStatus.AVAILABLE);

        repairerInMemoryRepository.add(r1);
        repairerInMemoryRepository.add(r2);
        repairerInMemoryRepository.add(r3);

        List<Repairer> sortedRepairers = repairerInMemoryRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1, r3, r2);
    }

    @Test
    void whenSortingRepairersByNameWithMixedNames_thenRepairersShouldBeSortedByNames() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Vita", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Alex", RepairerStatus.BUSY);
        Repairer r4 = new Repairer(4,"Alex", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(1,"Anton", RepairerStatus.BUSY);

        repairerInMemoryRepository.add(r1);
        repairerInMemoryRepository.add(r2);
        repairerInMemoryRepository.add(r3);
        repairerInMemoryRepository.add(r4);
        repairerInMemoryRepository.add(r5);

        List<Repairer> sortedRepairers= repairerInMemoryRepository.getAllSortedByName();
        assertThat(sortedRepairers).containsExactly(r3, r4, r5, r1, r2);
    }

    @Test
    void whenFindingExistingRepairerByName_thenRepairerShouldBeFound() {
        Repairer r1 = new Repairer(1,"Upol", RepairerStatus.BUSY);
        repairerInMemoryRepository.add(r1);

        Optional<Repairer> foundRepairer = repairerInMemoryRepository.findByName(r1.getName());
        assertThat(foundRepairer).isPresent();
        assertThat(foundRepairer.get()).isEqualTo(r1);
        assertThat(foundRepairer.get().getName()).isEqualTo("Upol");
        assertThat(foundRepairer.get().getStatus()).isEqualTo(RepairerStatus.BUSY);
    }

    @Test
    void whenFindingNonExistingRepairerByName_thenMustReturnEmpty() {
        String nonExistingName = "blabla";
        Optional<Repairer> notFoundedRepairer = repairerInMemoryRepository.findByName(nonExistingName);
        assertThat(notFoundedRepairer.isEmpty());
    }


    @Test
    void whenUpdatingExistingRepairer_thenRepairerMustBeUpdated() {
        Repairer oldRepairer = new Repairer(1, "Asan", RepairerStatus.AVAILABLE);
        repairerInMemoryRepository.add(oldRepairer);
        Repairer newRepairer = new Repairer(1, "Asan", RepairerStatus.BUSY);
        Repairer updatedRepairer = repairerInMemoryRepository.update(newRepairer);
        assertThat(updatedRepairer).isEqualTo(newRepairer);
        assertThat(repairerInMemoryRepository.getAll()).contains(updatedRepairer);
        assertThat(repairers.get(0).getStatus()).isEqualTo(RepairerStatus.BUSY);
    }

    @Test
    void whenUpdatingNonExistingSlot_thenExceptionMustBeThrown(){
        Repairer repairer = new Repairer(1, "someone", RepairerStatus.AVAILABLE);
        assertThatThrownBy(() -> repairerInMemoryRepository.update(repairer))
                .isInstanceOf(NoSuchElementException.class);
    }
}