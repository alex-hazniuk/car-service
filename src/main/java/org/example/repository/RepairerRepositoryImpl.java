package org.example.repository;

import org.example.exception.InvalidIdException;
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
    public Repairer findById(int id) {
        return repairers.stream()
                .filter(repairer -> repairer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidIdException("Can't find repairer by id: " + id));
    }

    @Override
    public List<Repairer> getAll() {
        return repairers;
    }

    @Override
    public Optional<Boolean> remove(String name) {
        return repairers.stream()
                .filter(r -> r.getName().equals(name)).map(repairers::remove)
                .findAny();
    }
}
