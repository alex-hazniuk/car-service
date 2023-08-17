package org.example.repository.InMemoryRepositories;

import org.example.model.Repairer;
import org.example.repository.RepairerRepository;

import java.util.List;
import java.util.Optional;

public class RepairerInMemoryRepository implements RepairerRepository {

    private final List<Repairer> repairers;

    private int id;

    public RepairerInMemoryRepository(List<Repairer> repairers) {
        this.repairers = repairers;
    }

    @Override
    public Repairer add(Repairer repairer) {
        repairer.setId(++id);
        repairers.add(repairer);
        return repairer;
    }

    @Override
    public Optional<Repairer> findById(int id) {
        return repairers.stream()
                .filter(repairer -> repairer.getId() == id)
                .findAny();
    }

    @Override
    public List<Repairer> getAll() {
        return repairers;
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return repairers.stream()
                .filter(repairer -> repairer.getName().equals(name))
                .findFirst();
    }

    @Override
    public boolean remove(int id) {
        Repairer repairer = repairers.remove(id);
        return repairer != null;
    }

    @Override
    public Repairer update(Repairer repairer) {
        repairers.set(repairers.indexOf(findById(repairer.getId()).orElseThrow()), repairer);
        return repairer;
    }
}
