package org.example.jpaRepository;

import org.example.model.GarageSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageSlotSpringJPARepository extends JpaRepository<GarageSlot, Integer> {

    List<GarageSlot> findAllByOrderByStatus();
}
