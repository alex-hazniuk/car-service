package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.RepairerRepository;
import org.example.repository.InMemoryRepositories.RepairerInMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepairerServiceImplTest {

    private static final String ARTEM = "Artem Dou";
    private static final String OLEG = "Oleg Ivanov";
    private final RepairerRepository repairerRepository = new RepairerInMemoryRepository(new ArrayList<>());
    private final RepairerServiceImpl repairerService = new RepairerServiceImpl(repairerRepository);

    @Test
    void whenSaveRepairerByName_thenRepairersShouldBeSaved() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer savedRepairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer savedRepairer2 = repairerRepository.findByName(OLEG).get();

        assertThat(repairerRepository.findByName(ARTEM)).isPresent();
        assertThat(repairerRepository.findByName(OLEG)).isPresent();
        assertThat(savedRepairer1).isEqualTo(repairerRepository.getAll().get(0));
        assertThat(savedRepairer2).isEqualTo(repairerRepository.getAll().get(1));
        assertThat(repairerRepository.getAll()).hasSize(2);
    }

    @Test
    void whenChangeStatus_thenRepairerStatusShouldChange() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer repairer = repairerService.findById(1);
        Repairer repairer2 = repairerService.findById(2);
        repairer2.setStatus(RepairerStatus.BUSY);


        assertThat(repairerService.changeStatus(repairer.getId()).getStatus())
                .isEqualTo(RepairerStatus.BUSY);
        assertThat(repairerService.changeStatus(repairer2.getId()).getStatus())
                .isEqualTo(RepairerStatus.AVAILABLE);
        assertThatThrownBy(() -> repairerService.findById(3))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("Can't find repairer by id: 3");
    }

    @Test
    void whenRemoveRepairByName_thenRepairerShouldBeRemoved() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        String ivan = "Ivan Orel";
        repairerService.save(ivan);
        repairerService.remove(ivan);

        assertThat(repairerRepository.getAll()).hasSize(2);
        assertThatThrownBy(() -> repairerService.findById(3))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("Can't find repairer by id: 3");
    }

    @Test
    void whenFindRepairById_thenRepairerShouldBeFound() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();

        assertThat(repairerRepository.findByName(ARTEM)).isPresent();
        assertThat(repairerRepository.findByName(OLEG)).isPresent();
        assertThat(repairerService.findById(1)).isEqualTo(repairer1);
        assertThat(repairerService.findById(2)).isEqualTo(repairer2);
        assertThatThrownBy(() -> repairerService.findById(3))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("Can't find repairer by id: 3");    }

    @Test
    void whenGetAll_thenAllRepairersShouldBeReturned() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();

        List<Repairer> list = List.of(repairer1, repairer2);

        assertThat(repairerRepository.findByName(ARTEM)).isPresent();
        assertThat(repairerRepository.findByName(OLEG)).isPresent();
        assertThat(repairerService.getAll()).isEqualTo(list);
    }

    @Test
    void whenSortRepairersByName_thenRepairersShouldBeSortedByName() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();

        List<Repairer> list = List.of(repairer1, repairer2);

        assertThat(repairerRepository.findByName(ARTEM)).isPresent();
        assertThat(repairerRepository.findByName(OLEG)).isPresent();
        assertThat(repairerService.sortedByName()).isEqualTo(list);
    }

    @Test
    void whenSortRepairersByStatus_thenRepairersShouldBeSortedByStatus() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();
        repairer1.setStatus(RepairerStatus.BUSY);
        List<Repairer> list = List.of(repairer2, repairer1);

        assertThat(repairerRepository.findByName(ARTEM)).isPresent();
        assertThat(repairerRepository.findByName(OLEG)).isPresent();
        assertThat(repairerService.sortedByStatus()).isEqualTo(list);
    }
}
