package org.example.repository;

import org.example.model.Repairer;

import java.util.List;
import java.util.Optional;

public interface RepairerRepository {

    Repairer add(Repairer repairer);

    Optional<Repairer> findById(long id);

    List<Repairer> getAll();

    List<Repairer> getAllSortedByStatus();

    List<Repairer> getAllSortedByName();

    boolean remove(long id);

    Repairer update(Repairer repairer);

    Optional<Repairer> findByName(String name);
}
