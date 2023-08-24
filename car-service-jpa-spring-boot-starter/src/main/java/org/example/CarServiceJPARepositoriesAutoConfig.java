package org.example;

import org.example.jpaRepository.GarageSlotSpringJPARepository;
import org.example.jpaRepository.OrderSpringJPARepository;
import org.example.jpaRepository.RepairerSpringJPARepository;
import org.example.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarServiceJPARepositoriesAutoConfig {

    @Bean
    RepairerRepository repairerRepository(RepairerSpringJPARepository repository) {
        return new RepairerJPARepository(repository);
    }

    @Bean
    GarageSlotRepository garageSlotRepository(GarageSlotSpringJPARepository repository) {
        return new GarageSlotJPARepository(repository);
    }

    @Bean
    OrderRepository orderRepository(OrderSpringJPARepository repository) {
        return new OrderJPARepository(repository);
    }
}
