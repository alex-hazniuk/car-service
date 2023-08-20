package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.RepairerStatus;

import java.util.Set;

/*@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repairer")
public class RepairerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private RepairerStatus status;

    @ManyToMany(mappedBy = "repairers")
    private Set<OrderEntity> orders;
}*/
