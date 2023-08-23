package org.example.management.actions.initServices;

import org.example.service.GarageSlotService;
import org.example.service.OrderService;
import org.example.service.RepairerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GenericInit {

    @Autowired
    private GarageSlotService garageSlotService;

    @Autowired
    private RepairerService repairerService;

    @Autowired
    private OrderService orderService;

}
