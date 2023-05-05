package com.backend.tjtablepartyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicSiteBriefDto {
    Long publicSiteId;
    String name;
    String picture;
    String city;
    String location;
    String[] type;
    float avgCost;
    int capacity;
    int gameNum;
}
