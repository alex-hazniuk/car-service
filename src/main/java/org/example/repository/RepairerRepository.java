package org.example.repository;

import org.example.model.Repairer;

import java.util.List;
import java.util.Optional;

public interface RepairerRepository {

    Repairer add(Repairer repairer);

    Optional<Repairer> findById(int id);

    List<Repairer> getAll();

    boolean remove(int id);

    Repairer update(Repairer repairer);

    Optional<Repairer> findByName(String name);
}
