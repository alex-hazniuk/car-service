package org.example.JdbcRepositiries;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.config.DataSource;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class GarageSlotJDBCRepositoryTest {

    private final GarageSlotJDBCRepository garageSlotJDBCRepository = new GarageSlotJDBCRepository();

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

    @AfterEach
    void tearDown() {
        garageSlotJDBCRepository.getAll().forEach(garageSlotJDBCRepository::delete);
    }


    // add()
    @Test
    void whenAddingSingleSlot_thenOneSlotMustBeAdded() {
        GarageSlot garageSlot = new GarageSlot();
        garageSlotJDBCRepository.add(garageSlot);
        List<GarageSlot> addedSlots = garageSlotJDBCRepository.getAll();
        assertThat(addedSlots).hasSize(1);

        GarageSlot savedGarageSlot = addedSlots.get(0);
        assertThat(savedGarageSlot).isNotNull();
        assertThat(savedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE);
    }

    @Test
    void whenAddingMultipleSlots_thenAllOfTHemMustBeAdded() {
        GarageSlot garageSlot1 = new GarageSlot();
        GarageSlot garageSlot2 = new GarageSlot();

        garageSlotJDBCRepository.add(garageSlot1);
        garageSlotJDBCRepository.add(garageSlot2);
        List<GarageSlot> addedSlots = garageSlotJDBCRepository.getAll();
        assertThat(addedSlots).hasSize(2);
        assertThat(addedSlots).allSatisfy(slot ->
                assertThat(slot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE)
        );
        assertThat(addedSlots).containsExactly(garageSlot1, garageSlot2);
    }

    // getAll()
    @Test
    void whenGettingAllGarageSlotsWithEmptyList_thenListShouldBeEmpty() {
        List<GarageSlot> result = garageSlotJDBCRepository.getAll();
        assertThat(result).isEmpty();
    }

    @Test
    void whenGettingAllGarageSlotsWithMultipleSlots_thenAllSlotsShouldBeReturned() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        GarageSlot garageSlot3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotJDBCRepository.add(garageSlot1);
        garageSlotJDBCRepository.add(garageSlot2);
        garageSlotJDBCRepository.add(garageSlot3);

        List<GarageSlot> results = garageSlotJDBCRepository.getAll();

        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(garageSlot1, garageSlot2, garageSlot3);
    }

    @Test
    void whenGettingAllGarageSlotsAfterRemovingSlots_thenRemainingSlotsShouldBeReturned() {
        GarageSlot garageSlot1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot garageSlot2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        garageSlotJDBCRepository.add(garageSlot1);
        garageSlotJDBCRepository.add(garageSlot2);

        garageSlotJDBCRepository.delete(garageSlot1);
        List<GarageSlot> result = garageSlotJDBCRepository.getAll();

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(garageSlot2);
    }

    // getAllSortedByStatus()
    @Test
    void whenSortingSlotsByStatusWithSingleSlot_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(6, GarageSlotStatus.UNAVAILABLE);
        garageSlotJDBCRepository.add(g1);

        List<GarageSlot> sortedSlots = garageSlotJDBCRepository.getAllSortedByStatus();
        assertThat(sortedSlots).containsExactly(g1);
        assertThat(g1.getStatus()).isEqualTo(GarageSlotStatus.UNAVAILABLE);
    }

    @Test
    void whenSortingSlotsByStatusWithMultipleSlotsSameStatus_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotJDBCRepository.add(g1);
        garageSlotJDBCRepository.add(g2);
        garageSlotJDBCRepository.add(g3);

        List<GarageSlot> sortedSlots = garageSlotJDBCRepository.getAllSortedByStatus();
        assertThat(sortedSlots).containsExactly(g1, g2, g3);
    }

    @Test
    void whenSortingSlotsByStatusWithMixedStatusSlots_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(11, GarageSlotStatus.UNAVAILABLE);
        GarageSlot g2 = new GarageSlot(12, GarageSlotStatus.AVAILABLE);
        GarageSlot g3 = new GarageSlot(13, GarageSlotStatus.AVAILABLE);
        GarageSlot g4 = new GarageSlot(14, GarageSlotStatus.UNAVAILABLE);
        garageSlotJDBCRepository.add(g1);
        garageSlotJDBCRepository.add(g2);
        garageSlotJDBCRepository.add(g3);
        garageSlotJDBCRepository.add(g4);

        List<GarageSlot> sortedSlots = garageSlotJDBCRepository.getAllSortedByStatus();
        assertThat(sortedSlots).containsExactly(g2, g3, g1, g4);
    }

    // findById() {
    @Test
    void whenFindingExistingSlotById_thenSlotShouldBeFound() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        garageSlotJDBCRepository.add(g1);

        Optional<GarageSlot> foundSlot = garageSlotJDBCRepository.findById(g1.getId());
        assertThat(foundSlot).isPresent();
        assertThat(foundSlot.get()).isEqualTo(g1);
    }

    @Test
    void whenFindingNonExistingSlotById_thenMustReturnEmpty() {
        int nonExistingId = 999;
        Optional<GarageSlot> notFoundSlot = garageSlotJDBCRepository.findById(nonExistingId);
        assertThat(notFoundSlot.isEmpty());
    }

    // delete()
    @Test
    void whenDeletingExistingSlot_thenSlotShouldBeDeletedAndNotFound() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot g2 = new GarageSlot(2, GarageSlotStatus.UNAVAILABLE);
        GarageSlot g3 = new GarageSlot(3, GarageSlotStatus.AVAILABLE);
        garageSlotJDBCRepository.add(g1);
        garageSlotJDBCRepository.add(g2);
        garageSlotJDBCRepository.add(g3);


        boolean toBeDeleted = garageSlotJDBCRepository.delete(g1);
        assertThat(toBeDeleted).isTrue();


        List<GarageSlot> remainingSlots = garageSlotJDBCRepository.getAll();
        assertThat(remainingSlots).hasSize(2);
        assertThat(garageSlotJDBCRepository.findById(g2.getId())).isNotNull();
        assertThat(garageSlotJDBCRepository.findById(g3.getId())).isNotNull();
    }

    @Test
    void whenRemovingNonExistingSlot_thenFalseMustBeReturned() {
        GarageSlot g1 = new GarageSlot();
        boolean deleted = garageSlotJDBCRepository.delete(g1);
        assertThat(deleted).isFalse();
    }

    // update()
    @Test
    void whenUpdatingExistingSlot_thenSlotMustBeUpdated() {
        GarageSlot oldGarageSlot = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        garageSlotJDBCRepository.add(oldGarageSlot);
        GarageSlot newGarageSlot = new GarageSlot(13, GarageSlotStatus.UNAVAILABLE);
        GarageSlot updatedGarageSlot = garageSlotJDBCRepository.update(newGarageSlot);
        assertThat(updatedGarageSlot).isEqualTo(newGarageSlot);
        assertThat(garageSlotJDBCRepository.getAll()).contains(updatedGarageSlot);
    }
}