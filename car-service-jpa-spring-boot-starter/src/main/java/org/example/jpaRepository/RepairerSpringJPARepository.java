package org.example.jpaRepository;

import org.example.model.Repairer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairerSpringJPARepository extends JpaRepository<Repairer, Integer> {

    List<Repairer> findAllByOrderByStatus();

    List<Repairer> findAllByOrderByName();

    Optional<Repairer> findByName(String name);

}
