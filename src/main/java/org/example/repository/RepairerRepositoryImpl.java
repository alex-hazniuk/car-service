package org.example.repository;

import org.example.model.Repairer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepairerRepositoryImpl implements RepairerRepository {

    private static final List<Repairer> repairers = new ArrayList<>();

    @Override
    public void add(Repairer repairer) {
        repairers.add(repairer);
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


}
