package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.RepairerRepository;
import org.example.repository.RepairerRepositoryImpl;
import org.junit.jupiter.api.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepairerServiceImplTest {

    private RepairerRepository repairerRepository;
    private RepairerServiceImpl repairerService;
    private final String name1 = "Artem Dou";
    private final String name2 = "Oleg Ivanov";


    @BeforeAll
    void init() {
        repairerRepository = new RepairerRepositoryImpl();
        repairerService = new RepairerServiceImpl(repairerRepository);
        repairerService.save(name1);
        repairerService.save(name2);
    }


    @Test
    void shouldSaveRepairerByName() {
        Repairer savedRepairer1 = repairerRepository.findByName(name1).get();
        Repairer savedRepairer2 = repairerRepository.findByName(name2).get();

        assertTrue(repairerRepository.findByName(name1).isPresent());
        assertTrue(repairerRepository.findByName(name2).isPresent());
        assertEquals(savedRepairer1, repairerRepository.getAll().get(0));
        assertEquals(savedRepairer2, repairerRepository.getAll().get(1));
        assertEquals(repairerRepository.getAll().size(), 2);
    }

    @Test
    void shouldChangeStatus() {
        Repairer repairer = repairerService.findById(1);
        Repairer repairer2 = repairerService.findById(2);
        repairer2.setStatus(RepairerStatus.BUSY);
        InvalidIdException exception = assertThrows(InvalidIdException.class,
                () -> repairerService.findById(3));

        assertEquals(RepairerStatus.BUSY, repairerService.changeStatus(repairer.getId())
                .getStatus());
        assertEquals(RepairerStatus.AVAILABLE, repairerService.changeStatus(repairer2.getId())
                .getStatus());
        assertEquals("Can't find repairer by id: 3", exception.getMessage());

    }

    @Test
    void shouldRemoveRepairByName() {
        String name3 = "Ivan Orel";
        repairerService.save(name3);
        repairerService.remove(name3);


        InvalidIdException exception = assertThrows(InvalidIdException.class,
                () -> repairerService.findById(3));

        assertEquals(repairerRepository.getAll().size(), 2);
        assertEquals("Can't find repairer by id: 3", exception.getMessage());
    }

    @Test
    void shouldFindRepairById() {
        Repairer repairer1 = repairerRepository.findByName(name1).get();
        Repairer repairer2 = repairerRepository.findByName(name2).get();
        InvalidIdException exception = assertThrows(InvalidIdException.class,
                () -> repairerService.findById(3));

        assertTrue(repairerRepository.findByName(name1).isPresent());
        assertTrue(repairerRepository.findByName(name2).isPresent());
        assertEquals(repairer1, repairerService.findById(1));
        assertEquals(repairer2, repairerService.findById(2));
        assertEquals("Can't find repairer by id: 3", exception.getMessage());

    }

    @Test
    void getAll() {
        Repairer repairer1 = repairerRepository.findByName(name1).get();
        Repairer repairer2 = repairerRepository.findByName(name2).get();

        List<Repairer> list = List.of(repairer1, repairer2);

        assertTrue(repairerRepository.findByName(name1).isPresent());
        assertTrue(repairerRepository.findByName(name2).isPresent());
        assertEquals(list, repairerService.getAll());

    }

    @Test
    void shouldSortRepairersByName() {

        Repairer repairer1 = repairerRepository.findByName(name1).get();
        Repairer repairer2 = repairerRepository.findByName(name2).get();

        List<Repairer> list = List.of(repairer1, repairer2);

        assertTrue(repairerRepository.findByName(name1).isPresent());
        assertTrue(repairerRepository.findByName(name2).isPresent());
        assertEquals(list, repairerService.sortedByName());

    }

    @Test
    void sortedByStatus() {
        Repairer repairer1 = repairerRepository.findByName(name1).get();
        Repairer repairer2 = repairerRepository.findByName(name2).get();
        repairer1.setStatus(RepairerStatus.BUSY);
        List<Repairer> list = List.of(repairer2, repairer1);

        assertTrue(repairerRepository.findByName(name1).isPresent());
        assertTrue(repairerRepository.findByName(name2).isPresent());
        assertEquals(list, repairerService.sortedByStatus());
    }
}