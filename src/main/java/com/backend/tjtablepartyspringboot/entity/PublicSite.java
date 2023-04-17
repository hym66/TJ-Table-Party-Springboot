package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "public_site")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicSite {
    @JsonSerialize(using= ToStringSerializer.class)
    Long publicSiteId;
    @JsonSerialize(using=ToStringSerializer.class)
    Long creatorId;
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
    String type;
    byte status;
}
