package org.example.service;

import org.example.model.Repairer;

import java.util.List;

public interface RepairerService {

    Repairer save(String name);

    Repairer changeStatus(long id);

    boolean remove(String name);

    boolean remove(long id);

    Repairer findById(long id);

    List<Repairer> getAll();

    List<Repairer> sortedByName();

    List<Repairer> sortedByStatus();
}
