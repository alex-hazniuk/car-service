package org.example.service.withJDBCRepositories;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.DataSource;
import org.example.exception.InvalidIdException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.service.GarageSlotService;
import org.example.service.JDBCService.JDBCGarageSlotServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GarageSlotServiceImplTest {

    private final GarageSlotJDBCRepository garageSlotRep = new GarageSlotJDBCRepository();
    private final GarageSlotService garageSlotService = new JDBCGarageSlotServiceImpl(garageSlotRep);

    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException {
        DataSource.setUrl("jdbc:h2:mem:test");
        DataSource dataSource = new DataSource();
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yml",
                new ClassLoaderResourceAccessor(),
                database);
        liquibase.update();
    }

    @Test
    void whenSavingSingleGarageSlot_thenSlotShouldBeSavedWithAvailableStatus() {
        garageSlotService.save();
        List<GarageSlot> savedSlots = garageSlotService.getAll();
        assertThat(savedSlots).hasSize(1);

        GarageSlot savedGarageSlot = savedSlots.get(0);
        assertThat(savedGarageSlot).isNotNull();
        assertThat(savedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE);

        garageSlotService.remove(savedGarageSlot.getId());
    }

    @Test
    void whenSavingMultipleGarageSlots_thenAllSlotsShouldBeSavedWithAvailableStatus() {
        GarageSlot garageSlot = garageSlotService.save();
        GarageSlot garageSlot1 = garageSlotService.save();
        List<GarageSlot> savedSlots = garageSlotService.getAll();
        assertThat(savedSlots).hasSize(2);

        assertThat(savedSlots).allSatisfy(slot ->
                assertThat(slot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE)
        );

        garageSlotService.remove(garageSlot1.getId());
        garageSlotService.remove(garageSlot.getId());
    }

    @Test
    void whenRemovingExistingSlot_thenSlotShouldBeRemovedAndNotFound() {
        GarageSlot g1 = garageSlotService.save();
        GarageSlot g2 = garageSlotService.save();

        boolean removed = garageSlotService.remove(g2.getId());
        assertTrue(removed);

        assertThatThrownBy(() -> garageSlotService.remove(g2.getId()))
                .isInstanceOf(InvalidIdException.class);

        List<GarageSlot> remainingSlots = garageSlotService.getAll();
        assertThat(remainingSlots).hasSize(1);

        assertThat(garageSlotService.findById(g1.getId())).isNotNull();

        garageSlotService.remove(g1.getId());
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

        garageSlotRep.delete(garageSlot1);
        garageSlotRep.delete(garageSlot2);
        garageSlotRep.delete(garageSlot3);
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

        garageSlotRep.delete(garageSlot2);
    }

    @Test
    void whenSortingSlotsByStatusWithSingleSlot_thenSlotsShouldBeSorted() {
        GarageSlot g1 = new GarageSlot(6, GarageSlotStatus.UNAVAILABLE);
        garageSlotRep.add(g1);

        List<GarageSlot> sortedSlots = garageSlotService.sortedByStatus();
        assertThat(sortedSlots).containsExactly(g1);

        garageSlotRep.delete(g1);
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

        garageSlotRep.delete(g1);
        garageSlotRep.delete(g2);
        garageSlotRep.delete(g3);
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

        garageSlotRep.delete(g1);
        garageSlotRep.delete(g2);
        garageSlotRep.delete(g3);
        garageSlotRep.delete(g4);
    }

    @Test
    void whenChangingStatusFromAvailableToUnavailable_thenStatusShouldBeUpdated() {
        GarageSlot initialGarageSlot = GarageSlot.builder()
                .status(GarageSlotStatus.AVAILABLE)
                .build();
        GarageSlot savedGarage = garageSlotRep.add(initialGarageSlot);

        GarageSlot updatedGarageSlot = garageSlotService.changeStatus(savedGarage.getId());
        assertThat(updatedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.UNAVAILABLE);

        garageSlotRep.delete(updatedGarageSlot);
    }

    @Test
    void whenChangingStatusFromUnavailableToAvailable_thenStatusShouldBeUpdated() {
        GarageSlot initialGarageSlot = GarageSlot.builder()
                .status(GarageSlotStatus.UNAVAILABLE)
                .build();
        GarageSlot savedGarage = garageSlotRep.add(initialGarageSlot);

        GarageSlot updatedGarageSlot = garageSlotService.changeStatus(savedGarage.getId());
        assertThat(updatedGarageSlot.getStatus()).isEqualTo(GarageSlotStatus.AVAILABLE);

        garageSlotRep.delete(updatedGarageSlot);
    }

    @Test
    void whenFindingExistingSlotById_thenSlotShouldBeFound() {
        GarageSlot g1 = new GarageSlot(1, GarageSlotStatus.AVAILABLE);
        GarageSlot savedGarage = garageSlotRep.add(g1);

        GarageSlot foundSlot = garageSlotService.findById(savedGarage.getId());
        assertThat(foundSlot).isEqualTo(g1);

        garageSlotRep.delete(foundSlot);
    }

    @Test
    void whenFindingNonExistingSlotById_thenInvalidIdExceptionShouldBeThrownWithMessage() {
        int nonExistingId = 999;

        assertThatThrownBy(() -> garageSlotService.findById(nonExistingId))
                .isInstanceOf(InvalidIdException.class)
                .hasMessageContaining("Can't find garageSlot by id: " + nonExistingId);
    }
}
