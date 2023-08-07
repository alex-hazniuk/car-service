package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Repairer {

    private int id;
    private String name;
    private RepairerStatus status = RepairerStatus.AVAILABLE;
}
