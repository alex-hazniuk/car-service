package org.example.service.JDBCService;

import org.example.exception.InvalidIdException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.RepairerRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JDBCRepairerServiceImpl implements JDBCRepairerService {
    private final RepairerRepository jdbcRepairerRepository;

    public JDBCRepairerServiceImpl(RepairerRepository jdbcRepairerRepository) {
        this.jdbcRepairerRepository = jdbcRepairerRepository;
    }

    @Override
    public Repairer save(String name) {
        Repairer repairer = Repairer.builder()
                .name(name)
                .status(RepairerStatus.AVAILABLE)
                .build();
        return jdbcRepairerRepository.add(repairer);
    }

    @Override
    public Repairer changeStatus(int id) {
        Repairer repairer = findById(id);
        if (repairer.getStatus() == RepairerStatus.AVAILABLE) {
            repairer.setStatus(RepairerStatus.BUSY);
        } else {
            repairer.setStatus(RepairerStatus.AVAILABLE);
        }
        return jdbcRepairerRepository.update(repairer);
    }

    @Override
    public boolean remove(int id) {
        return jdbcRepairerRepository.remove(id);
    }

    @Override
    public Repairer findById(int id) {
        return jdbcRepairerRepository
                .findById(id)
                .orElseThrow(() -> new InvalidIdException("Can't find repairer by id: " + id));
    }

    @Override
    public List<Repairer> getAll() {
        return jdbcRepairerRepository.getAll();
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
