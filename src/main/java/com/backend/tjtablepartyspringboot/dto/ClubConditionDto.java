package com.backend.tjtablepartyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubConditionDto {
    Set capacitySet;
    String city;
    String keyword;
    Float latitude;
    Float longitude;
    Float maxDistance;
    Float minDistance;
}
