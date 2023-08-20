package org.example.repository.JPARepositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.example.config.HibernateUtil;
import org.example.exception.DataProcessingException;
import org.example.model.Repairer;
import org.example.repository.RepairerRepository;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class RepairerJPARepository implements RepairerRepository {
    @Override
    public Repairer add(Repairer repairer) {
        EntityManager manager = null;
        EntityTransaction transaction = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(repairer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert repairer " + repairer, e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return repairer;
    }

    @Override
    public Optional<Repairer> findById(long id) {
        EntityManager manager = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            return Optional.ofNullable(manager.find(Repairer.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't find repairer by id: " + id, e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public List<Repairer> getAll() {
        EntityManager manager = null;
        try {
            manager = HibernateUtil.getInstance().createEntityManager();
            TypedQuery<Repairer> fromRepairer = manager.createQuery("from Repairer", Repairer.class);
            return fromRepairer.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all repairers ", e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public List<Repairer> getAllSortedByStatus() {
        return null;
    }

    @Override
    public List<Repairer> getAllSortedByName() {
        return null;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public Repairer update(Repairer repairer) {
        return null;
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return Optional.empty();
    }
}
