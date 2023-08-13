package org.example.repository.FileRepositories;

import org.example.model.Repairer;
import org.example.repository.RepairerRepository;

import java.util.List;
import java.util.Optional;

public class RepairerFileRepository implements RepairerRepository {

    private final CarServiceStoreHandler carServiceStoreHandler;

    public RepairerFileRepository(CarServiceStoreHandler carServiceStoreHandler) {
        this.carServiceStoreHandler = carServiceStoreHandler;
    }

    @Override
    public void add(Repairer repairer) {
        State state = carServiceStoreHandler.read();
        List<Repairer> repairers = state.repairers();
        repairers.add(repairer);
        carServiceStoreHandler.write(state.withRepairers(repairers));
    }

    @Override
    public Optional<Repairer> findById(int id) {
        return getAll().stream().filter(repairer -> (repairer.getId() == id)).findAny();
    }

    @Override
    public List<Repairer> getAll() {
        return carServiceStoreHandler.read().repairers();
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return getAll().stream().filter(repairer -> (repairer.getName().equals(name))).findAny();
    }

    @Override
    public boolean remove(Repairer repairer) {
        State state = carServiceStoreHandler.read();
        List<Repairer> repairers = state.repairers();
        boolean remove = repairers.remove(repairer);
        carServiceStoreHandler.write(state.withRepairers(repairers));

        return remove;
    }

    @Override
    public Repairer update(int index, Repairer repairer) {
        State state = carServiceStoreHandler.read();
        List<Repairer> repairers = state.repairers();
        Repairer updatedRepairer = repairers.set(index, repairer);
        carServiceStoreHandler.write(state.withRepairers(repairers));

        return updatedRepairer;
    }
}
