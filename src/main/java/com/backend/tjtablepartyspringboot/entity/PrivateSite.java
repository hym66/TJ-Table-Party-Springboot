package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@TableName(value = "private_site")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivateSite {
    @JsonSerialize(using= ToStringSerializer.class)
    Long privateSiteId;
    @JsonSerialize(using= ToStringSerializer.class)
    String creatorId;
    String name;
    String city;
    String location;
    String picture;
    String introduction;
    float latitude;
    float longitude;

    public PrivateSite(String creatorId, String name, String city, String location, String picture, String introduction, float latitude, float longitude) {
        this.creatorId = creatorId;
        this.name = name;
        this.city = city;
        this.location = location;
        this.picture = picture;
        this.introduction = introduction;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
