package org.example.JdbcRepositiries;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.config.DataSource;
import org.example.exception.DataProcessingException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RepairerJDBCRepositoryTest {
    private final RepairerJDBCRepository repairerJDBCRepository = new RepairerJDBCRepository();

    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException {
        DataSource.setUrl("jdbc:h2:mem:test");
        DataSource dataSource = new DataSource();
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setURL("jdbc:h2:mem:test");

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        Liquibase liquibase = new Liquibase("changelog/db.changelog-master.yml", new ClassLoaderResourceAccessor(), database);
        liquibase.update();
    }

    @BeforeEach
    void tearDown() {
        List<Repairer> repairers = repairerJDBCRepository.getAll();
        for (Repairer repairer : repairers) {
            repairerJDBCRepository.remove(repairer.getId());
        }
    }

    // add()
    @Test
    void whenAddingSingleRepairer_thenOneRepairerMustBeAdded() {
        Repairer repairer = new Repairer(1, "Maxim", RepairerStatus.AVAILABLE);
        repairerJDBCRepository.add(repairer);
        List<Repairer> addedRepairers = repairerJDBCRepository.getAll();
        assertThat(addedRepairers).hasSize(1);

        Repairer savedRepairer = addedRepairers.get(0);
        assertThat(savedRepairer).isNotNull();
        assertThat(savedRepairer.getStatus()).isEqualTo(RepairerStatus.AVAILABLE);
    }

    @Test
    void whenAddingMultipleSlots_thenAllOfTHemMustBeAdded() {
        Repairer repairer1 = new Repairer(1, "Alex", RepairerStatus.AVAILABLE);
        Repairer repairer2 = new Repairer(2, "Olga", RepairerStatus.BUSY);

        repairerJDBCRepository.add(repairer1);
        repairerJDBCRepository.add(repairer2);
        List<Repairer> addedRepairers = repairerJDBCRepository.getAll();
        assertThat(addedRepairers).hasSize(2);
        assertThat(addedRepairers).containsExactly(repairer1, repairer2);
    }


    @Test
    void whenFindingExistingRepairerById_thenRepairerShouldBeFound() {
        Repairer r1 = new Repairer(1, "Upol", RepairerStatus.BUSY);
        repairerJDBCRepository.add(r1);

        Optional<Repairer> foundRepairer = repairerJDBCRepository.findById(r1.getId());
        assertThat(foundRepairer).isPresent();
        assertThat(foundRepairer.get()).isEqualTo(r1);
        assertThat(foundRepairer.get().getName()).isEqualTo("Upol");
        assertThat(foundRepairer.get().getStatus()).isEqualTo(RepairerStatus.BUSY);
    }

    @Test
    void whenFindingNonExistingRepairerById_thenMustReturnEmpty() {
        int nonExistingId = 999;
        Throwable exception = assertThrows(DataProcessingException.class, () -> {
            repairerJDBCRepository.findById(nonExistingId);
        });

        assertThat(exception).hasMessageContaining("Can't get repairer by id: " + nonExistingId);
    }

    @Test
    void whenGettingAllRepairersWithMultipleSlots_thenAllRepairersShouldBeReturned() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Alex", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Evelina", RepairerStatus.BUSY);
        Repairer r4 = new Repairer(4,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(5,"Anton", RepairerStatus.BUSY);

        repairerJDBCRepository.add(r1);
        repairerJDBCRepository.add(r2);
        repairerJDBCRepository.add(r3);
        repairerJDBCRepository.add(r4);
        repairerJDBCRepository.add(r5);

        List<Repairer> results = repairerJDBCRepository.getAll();

        assertThat(results).hasSize(5);
        assertThat(results).containsExactly(r1, r2, r3, r4, r5);
    }

    @Test
    void whenSortingRepairerByStatus_thenRepairerShouldBeReturned() {
        Repairer r1 = new Repairer(6, "Upol", RepairerStatus.AVAILABLE);
        repairerJDBCRepository.add(r1);

        List<Repairer> sortedRepairers = repairerJDBCRepository.getAllSortedByStatus();
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

        repairerJDBCRepository.add(r1);
        repairerJDBCRepository.add(r2);
        repairerJDBCRepository.add(r3);
        repairerJDBCRepository.add(r4);
        repairerJDBCRepository.add(r5);

        List<Repairer> sortedRepairers = repairerJDBCRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1, r2, r3, r4, r5);
     }

    @Test
    void whenSortingRepairersByStatusWithMixedStatuses_thenRepairersShouldBeSortedByStatus() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Alex", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Evelina", RepairerStatus.BUSY);
        Repairer r4 = new Repairer(4,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(1,"Anton", RepairerStatus.BUSY);

        repairerJDBCRepository.add(r1);
        repairerJDBCRepository.add(r2);
        repairerJDBCRepository.add(r3);
        repairerJDBCRepository.add(r4);
        repairerJDBCRepository.add(r5);

        List<Repairer> sortedRepairers= repairerJDBCRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1, r2, r4, r3, r5);
    }

    @Test
    void whenSortingMultipleRepairersByNamesWithSameNames_thenRepairersShouldBeSortedByNames() {
        Repairer r1 = new Repairer(1,"Sergei", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Sergei", RepairerStatus.BUSY);
        Repairer r3 = new Repairer(3,"Sergei", RepairerStatus.AVAILABLE);

        repairerJDBCRepository.add(r1);
        repairerJDBCRepository.add(r2);
        repairerJDBCRepository.add(r3);

        List<Repairer> sortedRepairers = repairerJDBCRepository.getAllSortedByStatus();
        assertThat(sortedRepairers).containsExactly(r1, r3, r2);
    }

    @Test
    void whenSortingRepairersByNameWithMixedNames_thenRepairersShouldBeSortedByNames() {
        Repairer r1 = new Repairer(1,"Vita", RepairerStatus.AVAILABLE);
        Repairer r2 = new Repairer(2,"Vita", RepairerStatus.AVAILABLE);
        Repairer r3 = new Repairer(3,"Alex", RepairerStatus.BUSY);
        Repairer r4 = new Repairer(4,"Alex", RepairerStatus.AVAILABLE);
        Repairer r5 = new Repairer(1,"Anton", RepairerStatus.BUSY);

        repairerJDBCRepository.add(r1);
        repairerJDBCRepository.add(r2);
        repairerJDBCRepository.add(r3);
        repairerJDBCRepository.add(r4);
        repairerJDBCRepository.add(r5);

        List<Repairer> sortedRepairers= repairerJDBCRepository.getAllSortedByName();
        assertThat(sortedRepairers).containsExactly(r3, r4, r5, r1, r2);
    }

    @Test
    void whenFindingExistingRepairerByName_thenRepairerShouldBeFound() {
        Repairer r1 = new Repairer(1,"Upol", RepairerStatus.BUSY);
        repairerJDBCRepository.add(r1);

        Optional<Repairer> foundRepairer = repairerJDBCRepository.findByName(r1.getName());
        assertThat(foundRepairer).isPresent();
        assertThat(foundRepairer.get()).isEqualTo(r1);
        assertThat(foundRepairer.get().getName()).isEqualTo("Upol");
        assertThat(foundRepairer.get().getStatus()).isEqualTo(RepairerStatus.BUSY);
    }

    @Test
    void whenFindingNonExistingRepairerByName_thenMustReturnEmpty() {
        String nonExistingName = "blabla";

        Throwable exception = assertThrows(DataProcessingException.class, () -> {
            repairerJDBCRepository.findByName(nonExistingName);
        });

        assertThat(exception).hasMessageContaining("Can't find repairer by name: " + nonExistingName);
    }


}