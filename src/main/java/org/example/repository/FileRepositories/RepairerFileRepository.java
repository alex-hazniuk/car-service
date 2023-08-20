package org.example.repository.FileRepositories;

import org.example.model.Repairer;
import org.example.repository.RepairerRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RepairerFileRepository implements RepairerRepository {

    private final CarServiceStoreHandler carServiceStoreHandler;

    private long id;

    public RepairerFileRepository(CarServiceStoreHandler carServiceStoreHandler) {
        this.carServiceStoreHandler = carServiceStoreHandler;
        this.id = getAll().size();
    }

    @Override
    public Repairer add(Repairer repairer) {
        State state = carServiceStoreHandler.read();
        List<Repairer> repairers = state.repairers();
        repairer.setId(++id);
        repairers.add(repairer);
        carServiceStoreHandler.write(state.withRepairers(repairers));
        return repairer;
    }

    @Override
    public Optional<Repairer> findById(long id) {
        return getAll().stream().filter(repairer -> (repairer.getId() == id)).findAny();
    }

    @Override
    public List<Repairer> getAll() {
        return carServiceStoreHandler.read().repairers();
    }

    @Override
    public List<Repairer> getAllSortedByStatus() {
        return getAll().stream()
                .sorted(Comparator.comparing(Repairer::getStatus))
                .toList();
    }

    @Override
    public List<Repairer> getAllSortedByName() {
        return getAll().stream()
                .sorted(Comparator.comparing(Repairer::getName))
                .toList();
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return getAll().stream().filter(repairer -> (repairer.getName().equals(name))).findAny();
    }

    @Override
    public boolean remove(long id) {
        State state = carServiceStoreHandler.read();
        List<Repairer> repairers = state.repairers();
        boolean removed = repairers.remove(id);
        carServiceStoreHandler.write(state.withRepairers(repairers));

        return removed;
    }

    @Override
    public Repairer update(Repairer repairer) {
        State state = carServiceStoreHandler.read();
        List<Repairer> repairers = state.repairers();
        repairers.set(repairers.indexOf(findById(repairer.getId()).orElseThrow()), repairer);
        carServiceStoreHandler.write(state.withRepairers(repairers));

        return repairer;
    }
}
