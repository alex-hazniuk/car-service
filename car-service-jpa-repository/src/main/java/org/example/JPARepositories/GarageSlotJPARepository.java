package org.example.JPARepositories;

import jakarta.persistence.PersistenceException;
import org.example.GarageSlotRepository;
import org.example.HibernateUtil;
import org.example.exception.DataProcessingException;
import org.example.model.GarageSlot;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class GarageSlotJPARepository implements GarageSlotRepository {

    @Override
    public GarageSlot add(GarageSlot garageSlot) {
        EntityManager manager = null;
        EntityTransaction transaction = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(garageSlot);
            transaction.commit();
            return garageSlot;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert garage slot " + garageSlot, e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public List<GarageSlot> getAll() {
        EntityManager manager = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            var garageSlot = manager.createQuery("from GarageSlot", GarageSlot.class);
            return garageSlot.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all repairers ", e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public List<GarageSlot> getAllSortedByStatus() {
        EntityManager manager = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            var fromGarageSlotOrderByStatus =
                    manager.createQuery("from GarageSlot order by status", GarageSlot.class);
            return fromGarageSlotOrderByStatus.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't sort garage slots by status ", e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        try {
            return Optional.ofNullable(HibernateUtil
                    .getInstance()
                    .createEntityManager()
                    .find(GarageSlot.class, id));
        } catch (PersistenceException e) {
            throw new DataProcessingException("Garage slot with id: " + id + "wasn't found.", e);
        }
    }

    @Override
    public boolean delete(GarageSlot garageSlot) {
        EntityManager manager = null;
        EntityTransaction transaction = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            GarageSlot removed = manager.find(GarageSlot.class, garageSlot.getId());
            manager.remove(removed);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete garage slot " + garageSlot, e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public GarageSlot update(GarageSlot garageSlot) {
        EntityManager manager = null;
        EntityTransaction transaction = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            manager.merge(garageSlot);
            transaction.commit();
            return garageSlot;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't sort garage slots by status ", e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
}
