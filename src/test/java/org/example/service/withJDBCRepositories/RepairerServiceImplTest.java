package org.example.service.withJDBCRepositories;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.config.DataSource;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.repository.RepairerRepository;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepairerServiceImplTest {

    private static final String ARTEM = "Artem Dou";
    private static final String OLEG = "Oleg Ivanov";
    private final RepairerRepository repairerRepository = new RepairerJDBCRepository();
    private final RepairerService repairerService = new RepairerServiceImpl(repairerRepository);

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

        repairerService.remove(savedRepairer1.getId());
        repairerService.remove(savedRepairer2.getId());
    }

    @Test
    void whenChangeStatus_thenRepairerStatusShouldChange() {
        Repairer r1 = repairerService.save(ARTEM);
        Repairer r2 = repairerService.save(OLEG);

        Repairer repairer = repairerService.findById(r1.getId());
        Repairer repairer2 = repairerService.findById(r2.getId());


        assertThat(repairerService.changeStatus(repairer.getId()).getStatus())
                .isEqualTo(RepairerStatus.BUSY);
        assertThat(repairerService.changeStatus(repairer.getId()).getStatus())
                .isEqualTo(RepairerStatus.AVAILABLE);
        assertThatThrownBy(() -> repairerService.findById(3))
                .hasMessage("Can't get repairer by id: 3");

        repairerService.remove(repairer.getId());
        repairerService.remove(repairer2.getId());
    }

    @Test
    void whenRemoveRepairByName_thenRepairerShouldBeRemoved() {
        Repairer r1 = repairerService.save(ARTEM);
        Repairer r2 = repairerService.save(OLEG);

        String ivan = "Ivan Orel";
        Repairer repairer = repairerService.save(ivan);
        repairerService.remove(repairer.getId());

        assertThat(repairerRepository.getAll()).hasSize(2);
        assertThatThrownBy(() -> repairerService.findById(repairer.getId()))
                .hasMessage("Can't get repairer by id: " + repairer.getId());

        repairerService.remove(r1.getId());
        repairerService.remove(r2.getId());
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
                .hasMessage("Can't get repairer by id: 3");

        repairerService.remove(repairer1.getId());
        repairerService.remove(repairer2.getId());
    }

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

        repairerService.remove(repairer1.getId());
        repairerService.remove(repairer2.getId());
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

        repairerService.remove(repairer1.getId());
        repairerService.remove(repairer2.getId());
    }

    @Test
    void whenSortRepairersByStatus_thenRepairersShouldBeSortedByStatus() {
        repairerService.save(ARTEM);
        repairerService.save(OLEG);

        Repairer repairer1 = repairerRepository.findByName(ARTEM).get();
        Repairer repairer2 = repairerRepository.findByName(OLEG).get();
        repairerService.changeStatus(repairer1.getId());
        repairer1.setStatus(RepairerStatus.BUSY);
        List<Repairer> list = List.of(repairer2, repairer1);

        assertThat(repairerRepository.findByName(ARTEM)).isPresent();
        assertThat(repairerRepository.findByName(OLEG)).isPresent();
        assertThat(repairerService.sortedByStatus()).isEqualTo(list);

        repairerService.remove(repairer1.getId());
        repairerService.remove(repairer2.getId());
    }
}
