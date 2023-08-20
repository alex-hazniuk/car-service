package org.example.repository.JPARepositories;

import jakarta.persistence.PersistenceException;
import org.example.config.HibernateUtil;
import org.example.exception.DataProcessingException;
import org.example.model.GarageSlot;
import org.example.repository.GarageSlotRepository;
import org.hibernate.JDBCException;

import java.util.List;
import java.util.Optional;

public class GarageSlotJPARepository implements GarageSlotRepository {

    /*private final EntityManager em;

    public GarageSlotJPARepository() {
        this.em = HibernateUtil.getEntityManager();
    }*/

    @Override
    public GarageSlot add(GarageSlot garageSlot) {
        try {
            HibernateUtil
                    .getInstance()
                    .createEntityManager()
                    .persist(garageSlot);
            return garageSlot;
        } catch (JDBCException e) {
            throw new DataProcessingException(garageSlot + "wasn't save.", e);
        }
    }

    @Override
    public List<GarageSlot> getAll() {
        return null;
    }

    @Override
    public List<GarageSlot> getAllSortedByStatus() {
        return null;
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        try {
            return Optional.of(HibernateUtil
                    .getInstance()
                    .createEntityManager()
                    .find(GarageSlot.class, id));
        } catch (PersistenceException e) {
            throw new DataProcessingException("Garage slot with id: " + id + "wasn't found.", e);
        }
    }

    @Override
    public boolean delete(GarageSlot garageSlot) {
        return false;
    }

    @Override
    public GarageSlot update(GarageSlot garageSlot) {
        return null;
    }
}
