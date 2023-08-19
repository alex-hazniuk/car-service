package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToMany
    @JoinTable(name = "order_repairer",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "repairer_id")})
    private Set<RepairerEntity> repairers;

    @ManyToOne
    @JoinColumn(name = "garage_slot_id")
  /*  @JoinTable(name = "garage_slot",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "garage_slot_id")})*/
    private GarageSlotEntity garageSlot;

    private Double price;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
}
