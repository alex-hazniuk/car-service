package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class GarageSlot {

    private int id;
    private GarageSlotStatus status = GarageSlotStatus.AVAILABLE;

}
