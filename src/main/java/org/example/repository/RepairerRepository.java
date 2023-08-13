package org.example.repository;

import org.example.model.Repairer;

import java.util.List;
import java.util.Optional;

public interface RepairerRepository {

    void add(Repairer repairer);

    Optional<Repairer> findById(int id);

    List<Repairer> getAll();

    boolean remove(Repairer repairer);

    Repairer update(int index, Repairer repairer);

    Optional<Repairer> findByName(String name);
}
