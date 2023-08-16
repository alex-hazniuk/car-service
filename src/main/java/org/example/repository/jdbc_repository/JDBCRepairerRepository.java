package org.example.repository.jdbc_repository;

import org.example.model.Repairer;

import java.util.List;
import java.util.Optional;

public interface JDBCRepairerRepository {
    Repairer add(Repairer repairer);

    Optional<Repairer> findById(int id);

    List<Repairer> getAll();

    boolean remove(int id);

    Repairer update(Repairer repairer);

    Repairer findByName(String name);
}
