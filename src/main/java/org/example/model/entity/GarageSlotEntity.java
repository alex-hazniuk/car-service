package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.GarageSlotStatus;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "garage_slot")
public class GarageSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private GarageSlotStatus status;

}
