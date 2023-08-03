package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.example.exception.InvalidNameException;
import org.example.repository.RepairerRepository;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RepairerServiceImpl implements RepairerService {

    private final RepairerRepository repairerDao;

    private int repairerId;

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
        try {
            return repairerDao.remove(name);
        } catch (InvalidNameException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Repairer findById(int id) {
        try {
            return repairerDao.findById(id);
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
            return new Repairer();
        }
    }

    @Override
    public List<Repairer> getAll() {
        return repairerDao.getAll();
    }

    @Override
    public List<Repairer> sortedByName() {
        return getAll().stream()
                .sorted(Comparator.comparing(Repairer::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Repairer> sortedByStatus() {
        return getAll().stream()
                .sorted(Comparator.comparing(Repairer::getStatus))
                .collect(Collectors.toList());
    }
}
