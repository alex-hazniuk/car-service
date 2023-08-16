package org.example.repository.jdbc_repository;

import org.example.model.Repairer;

import java.util.List;

public interface JDBCRepairerRepository {
    Repairer add(Repairer repairer);

    Repairer findById(int id);

    List<Repairer> getAll();

    boolean remove(int id);

    Repairer update(Repairer repairer);

    Repairer findByName(String name);
}
