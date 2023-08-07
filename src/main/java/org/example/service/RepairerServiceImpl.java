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

    private final RepairerRepository repairerRepository;

    private int repairerId;

    @Override
    public void save(String name) {
        Repairer repairer = Repairer.builder()
                .id(++repairerId)
                .status(RepairerStatus.AVAILABLE)
                .name(name)
                .build();
        repairerRepository.add(repairer);
    }

    @Override
    public Repairer changeStatus(int id) {
        Repairer repairer = findById(id);
        if (repairer.getStatus() == RepairerStatus.AVAILABLE) {
            repairer.setStatus(RepairerStatus.BUSY);
        } else {
            repairer.setStatus(RepairerStatus.AVAILABLE);
        }
        return repairer;
    }

    @Override
    public boolean remove(String name) {
        Repairer repairer = repairerRepository
                .findByName(name)
                .orElseThrow(() ->
                        new InvalidNameException("Can't find repairer by name: " + name));
        return getAll().remove(repairer);
    }

    @Override
    public Repairer findById(int id) {
            return repairerRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new InvalidIdException("Can't find repairer by id: " + id));
    }

    @Override
    public List<Repairer> getAll() {
        return repairerRepository.getAll();
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
