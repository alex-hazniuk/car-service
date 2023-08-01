package org.example.service;

import org.example.dao.RepairerDaoImpl;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;

import java.util.Comparator;
import java.util.List;

public class RepairerServiceImpl implements RepairerService {
    private final RepairerDaoImpl repairerDao;
    private int repairerId;

    public RepairerServiceImpl(RepairerDaoImpl repairerDao) {
        this.repairerDao = repairerDao;
    }

    @Override
    public void save(String name) {
        Repairer repairer = Repairer.builder()
                .id(++repairerId)
                .name(name)
                .status(RepairerStatus.AVAILABLE)
                .build();
        repairerDao.add(repairer);
    }

    @Override
    public Repairer changeStatus(Repairer repairer) {
        if (repairer.getStatus() == RepairerStatus.AVAILABLE) {
            repairer.setStatus(RepairerStatus.BUSY);
        } else {
            repairer.setStatus(RepairerStatus.AVAILABLE);
        }
        return repairer;
    }

    @Override
    public boolean remove(String name) {
        return repairerDao.remove(name);
    }

    @Override
    public Repairer findById(int id) {
        return repairerDao.findById(id);
    }

    @Override
    public List<Repairer> getAll() {
        return repairerDao.getAll();
    }

    @Override
    public List<Repairer> sortedByName() {
        return getAll().stream()
                .sorted(Comparator.comparing(Repairer::getName))
                .toList();
    }

    @Override
    public List<Repairer> sortedByStatus() {
        return getAll().stream()
                .sorted(Comparator.comparing(Repairer::getStatus))
                .toList();
    }
}
