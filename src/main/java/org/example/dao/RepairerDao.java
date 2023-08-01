package org.example.dao;

import org.example.model.Repairer;

import java.util.List;

public interface RepairerDao {
    void add(Repairer repairer);

    Repairer findById(int id);

    List<Repairer> getAll();

    boolean remove(String name);
}
