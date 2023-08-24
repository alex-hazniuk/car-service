package org.example.repository;

import org.example.RepairerRepository;
import org.example.exception.DataProcessingException;
import org.example.jpaRepository.RepairerSpringJPARepository;
import org.example.model.Repairer;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public class RepairerJPARepository implements RepairerRepository {

    private final RepairerSpringJPARepository repository;

    public RepairerJPARepository(RepairerSpringJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public Repairer add(Repairer repairer) {
        return repository.save(repairer);
    }

    @Override
    public Optional<Repairer> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Repairer> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Repairer> getAllSortedByStatus() {
        return repository.findAllByOrderByStatus();
    }

    @Override
    public List<Repairer> getAllSortedByName() {
        return repository.findAllByOrderByName();
    }

    @Override
    public boolean remove(int id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new DataProcessingException("Can't remove repairer by id: " + id, e);
        }
    }

    @Override
    public Repairer update(Repairer repairer) {
        return repository.save(repairer);
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return repository.findByName(name);
    }
}
