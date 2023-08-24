package org.example.repository;
import org.example.GarageSlotRepository;
import org.example.exception.DataProcessingException;
import org.example.jpaRepository.GarageSlotSpringJPARepository;
import org.example.model.GarageSlot;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public class GarageSlotJPARepository implements GarageSlotRepository {

    private final GarageSlotSpringJPARepository repository;

    public GarageSlotJPARepository(GarageSlotSpringJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public GarageSlot add(GarageSlot garageSlot) {
        return repository.save(garageSlot);
    }

    @Override
    public List<GarageSlot> getAll() {
        return repository.findAll();
    }

    @Override
    public List<GarageSlot> getAllSortedByStatus() {
        return repository.findAllByOrderByStatus();
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean delete(GarageSlot garageSlot) {
        try {
            repository.delete(garageSlot);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new DataProcessingException("Can't remove garage slot: " + garageSlot, e);
        }
    }

    @Override
    public GarageSlot update(GarageSlot garageSlot) {
        return repository.save(garageSlot);
    }
}
