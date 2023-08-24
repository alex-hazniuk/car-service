package org.example;

import org.example.jpaRepository.GarageSlotSpringJPARepository;
import org.example.jpaRepository.OrderSpringJPARepository;
import org.example.jpaRepository.RepairerSpringJPARepository;
import org.example.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarServiceJPARepositoriesAutoConfig {
    private RepairerSpringJPARepository repairerSpringJPARepository;
    private GarageSlotSpringJPARepository garageSlotSpringJPARepository;
    private OrderSpringJPARepository orderSpringJPARepository;

    @Bean
    RepairerRepository repairerRepository() {
        return new RepairerJPARepository(repairerSpringJPARepository);
    }

    @Bean
    GarageSlotRepository garageSlotRepository() {
        return new GarageSlotJPARepository(garageSlotSpringJPARepository);
    }

    @Bean
    OrderRepository orderRepository() {
        return new OrderJPARepository(orderSpringJPARepository);
    }
}
