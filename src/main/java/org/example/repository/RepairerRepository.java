package org.example.repository;

import org.example.model.Repairer;

import java.util.List;

public interface RepairerRepository {

    void add(Repairer repairer);

    Repairer findById(int id);

    List<Repairer> getAll();

    boolean remove(String name);
}
