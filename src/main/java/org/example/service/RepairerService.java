package org.example.service;

import org.example.model.Repairer;

import java.util.List;

public interface RepairerService {

    Repairer save(String name);

    Repairer changeStatus(int id);

    boolean remove(String name);

    boolean remove(int id);

    Repairer findById(int id);

    List<Repairer> getAll();

    List<Repairer> sortedByName();

    List<Repairer> sortedByStatus();
}
