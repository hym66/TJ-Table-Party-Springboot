package com.backend.tjtablepartyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/5/9
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteConditionDto {
    Integer maxCapacity;
    Integer minCapacity;
    String city;
    String keyword;
    Float latitude;
    Float longitude;
    Float maxDistance;
    Float minDistance;
}
