package org.example.repository.JPARepositories;

import org.example.model.Repairer;
import org.example.repository.RepairerRepository;

import java.util.List;
import java.util.Optional;

public class RepairerJPARepository implements RepairerRepository {
    @Override
    public Repairer add(Repairer repairer) {
        return null;
    }

    @Override
    public Optional<Repairer> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Repairer> getAll() {
        return null;
    }

    @Override
    public List<Repairer> getAllSortedByStatus() {
        return null;
    }

    @Override
    public List<Repairer> getAllSortedByName() {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public Repairer update(Repairer repairer) {
        return null;
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return Optional.empty();
    }
}
