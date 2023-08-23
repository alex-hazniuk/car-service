package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.GarageSlot;
import org.example.service.GarageSlotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/garage-slots")
public class GarageSlotController {

    private final GarageSlotService garageSlotService;

    @GetMapping
    public List<GarageSlot> getAll() {
        return garageSlotService.getAll();
    }
}
