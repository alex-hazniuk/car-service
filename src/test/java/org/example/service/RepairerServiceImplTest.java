package org.example.service;

import org.example.exception.InvalidIdException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.RepairerRepository;
import org.example.repository.RepairerRepositoryImpl;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepairerServiceImplTest {
    private static final String ARTEM = "Artem Dou";
    private static final String OLEG = "Oleg Ivanov";
    private RepairerRepository repairerRepository;
    private RepairerServiceImpl repairerService;


    @BeforeEach
    void init() {
        repairerRepository = new RepairerRepositoryImpl(new ArrayList<>());
        repairerService = new RepairerServiceImpl(repairerRepository);
        repairerService.save(ARTEM);
        repairerService.save(OLEG);
    }

    @AfterEach
    public void tearDown() {
        repairerService.getAll().clear();
    }

    @Test
    void shouldSaveRepairerByName() {
        Repairer savedRepairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer savedRepairer2 = repairerRepository.findByName(OLEG).get();

        assertTrue(repairerRepository.findByName(ARTEM).isPresent());
        assertTrue(repairerRepository.findByName(OLEG).isPresent());
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
        String ivan = "Ivan Orel";
        repairerService.save(ivan);
        repairerService.remove(ivan);


        InvalidIdException exception = assertThrows(InvalidIdException.class,
                () -> repairerService.findById(3));

        assertEquals(repairerRepository.getAll().size(), 2);
        assertEquals("Can't find repairer by id: 3", exception.getMessage());
    }

    @Test
    void shouldFindRepairById() {
        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();
        InvalidIdException exception = assertThrows(InvalidIdException.class,
                () -> repairerService.findById(3));

        assertTrue(repairerRepository.findByName(ARTEM).isPresent());
        assertTrue(repairerRepository.findByName(OLEG).isPresent());
        assertEquals(repairer1, repairerService.findById(1));
        assertEquals(repairer2, repairerService.findById(2));
        assertEquals("Can't find repairer by id: 3", exception.getMessage());

    }

    @Test
    void getAll() {
        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();

        List<Repairer> list = List.of(repairer1, repairer2);

        assertTrue(repairerRepository.findByName(ARTEM).isPresent());
        assertTrue(repairerRepository.findByName(OLEG).isPresent());
        assertEquals(list, repairerService.getAll());

    }

    @Test
    void shouldSortRepairersByName() {

        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();

        List<Repairer> list = List.of(repairer1, repairer2);

        assertTrue(repairerRepository.findByName(ARTEM).isPresent());
        assertTrue(repairerRepository.findByName(OLEG).isPresent());
        assertEquals(list, repairerService.sortedByName());

    }

    @Test
    void sortedByStatus() {
        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();
        repairer1.setStatus(RepairerStatus.BUSY);
        List<Repairer> list = List.of(repairer2, repairer1);

        assertTrue(repairerRepository.findByName(ARTEM).isPresent());
        assertTrue(repairerRepository.findByName(OLEG).isPresent());
        assertEquals(list, repairerService.sortedByStatus());
    }
}
