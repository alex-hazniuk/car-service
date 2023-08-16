package org.example.service.JDBCService;

import org.example.model.Repairer;

import java.util.List;

public interface JDBCRepairerService {
    Repairer save(String name);

    Repairer changeStatus(int id);

    boolean remove(int id);

    Repairer findById(int id);

    List<Repairer> getAll();

    List<Repairer> sortedByName();

    List<Repairer> sortedByStatus();
}
