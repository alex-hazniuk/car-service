package org.example.repository;

import org.example.model.Repairer;

import java.util.List;
import java.util.Optional;

public interface RepairerRepository {

    void add(Repairer repairer);

    Optional<Repairer> findById(int id);

    List<Repairer> getAll();

    //Optional<Boolean> remove(String name);
    Optional<Repairer> findByName(String name);
}
