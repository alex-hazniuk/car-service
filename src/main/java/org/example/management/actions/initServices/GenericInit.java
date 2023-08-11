package org.example.management.actions.initServices;

import lombok.Getter;
import org.example.repository.*;
import org.example.repository.FileRepositories.CarServiceStoreHandler;
import org.example.repository.FileRepositories.GarageSlotFileRepository;
import org.example.repository.FileRepositories.OrderFileRepository;
import org.example.repository.FileRepositories.RepairerFileRepository;
import org.example.service.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class GenericInit {

    private final Path path = Paths.get("src/main/resources/state.json");

    @Getter
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(
            new GarageSlotFileRepository(new CarServiceStoreHandler(path)));

    @Getter
    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerFileRepository(new CarServiceStoreHandler(path)));

    @Getter
    private final OrderRepository orderRepository = new OrderFileRepository(new CarServiceStoreHandler(path));

    @Getter
     private final OrderService orderService = new OrderServiceImpl(orderRepository,
            repairerService, garageSlotService);

}
