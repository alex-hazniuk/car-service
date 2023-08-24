package org.example.jpaRepository;

import org.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSpringJPARepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByStatus();

    List<Order> findAllByOrderByPrice();

    List<Order> findAllByOrderByCreatedAt();

    List<Order> findAllByOrderByCompletedAt();
}
