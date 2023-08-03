package org.example.repository;

import org.example.exception.InvalidIdException;
import org.example.exception.InvalidNameException;
import org.example.model.Repairer;

import java.util.ArrayList;
import java.util.List;

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
    public boolean remove(String name) {
        Repairer repairer = repairers.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidNameException("No such repairer: " + name));
        return repairers.remove(repairer);
    }


}
