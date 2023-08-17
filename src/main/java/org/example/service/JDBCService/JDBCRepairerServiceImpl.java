package org.example.service.JDBCService;

import org.example.exception.InvalidIdException;
import org.example.exception.InvalidNameException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.RepairerRepository;
import org.example.service.RepairerService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JDBCRepairerServiceImpl implements RepairerService {
    private final RepairerRepository repairerRepository;

    public JDBCRepairerServiceImpl(RepairerRepository repairerRepository) {
        this.repairerRepository = repairerRepository;
    }

    @Override
    public Repairer save(String name) {
        Repairer repairer = Repairer.builder()
                .name(name)
                .status(RepairerStatus.AVAILABLE)
                .build();
        return repairerRepository.add(repairer);
    }

    @Override
    public Repairer changeStatus(int id) {
        Repairer repairer = findById(id);
        if (repairer.getStatus() == RepairerStatus.AVAILABLE) {
            repairer.setStatus(RepairerStatus.BUSY);
        } else {
            repairer.setStatus(RepairerStatus.AVAILABLE);
        }
        return repairerRepository.update(repairer);
    }

    @Override
    public boolean remove(String name) {
        Repairer repairer = repairerRepository
                .findByName(name)
                .orElseThrow(() -> new InvalidNameException("Can't find repairer by name: " + name));
        return repairerRepository.remove(repairer.getId());
    }

    @Override
    public boolean remove(int id) {
        return repairerRepository.remove(id);
    }

    @Override
    public Repairer findById(int id) {
        return repairerRepository
                .findById(id)
                .orElseThrow(() -> new InvalidIdException("Can't find repairer by id: " + id));
    }

    @Override
    public List<Repairer> getAll() {
        return repairerRepository.getAll();
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
                .collect(Collectors.toList());
    }
}
