package com.backend.tjtablepartyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/6/10
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicSiteModifyDto {
    Long publicSiteId;
    String creatorId;
    String name;
    String city;
    String location;
    String picture;
    String introduction;
    float avgCost;
    int capacity;
    int gameNum;
    String phone;
    Date uploadTime;
    Date checkTime;
    int status;
    String type;
    String tag;
    float latitude;
    float longitude;
    String locationTitle;
    String openTime;
}
