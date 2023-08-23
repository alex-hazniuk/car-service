package org.example;

import org.example.FileRepositories.CarServiceStoreHandler;
import org.example.FileRepositories.GarageSlotFileRepository;
import org.example.FileRepositories.OrderFileRepository;
import org.example.FileRepositories.RepairerFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class CarServiceOnFileRepositoriesAutoConfig {

    @Value("${state-file.path}")
    String path;

    @Bean
    RepairerRepository repairerRepository() {
        return new RepairerFileRepository(new CarServiceStoreHandler(Paths.get(path)));
    }

    @Bean
    GarageSlotRepository garageSlotRepository() {
        return new GarageSlotFileRepository(new CarServiceStoreHandler(Paths.get(path)));
    }

    @Bean
    OrderRepository orderRepository() {
        return new OrderFileRepository(new CarServiceStoreHandler(Paths.get(path)));
    }
}
