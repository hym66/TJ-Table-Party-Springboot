package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
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
    Long adminId;
    String adminPhotos;
    String adminMessage;

    public PublicSite(String creatorId, String name, String city, String location, String picture, String introduction, float avgCost, int capacity, int gameNum, String phone, Date uploadTime, int status, String type, String tag, float latitude, float longitude, String locationTitle) {
        this.creatorId = creatorId;
        this.name = name;
        this.city = city;
        this.location = location;
        this.picture = picture;
        this.introduction = introduction;
        this.avgCost = avgCost;
        this.capacity = capacity;
        this.gameNum = gameNum;
        this.phone = phone;
        this.uploadTime = uploadTime;
        this.status = status;
        this.type = type;
        this.tag = tag;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationTitle = locationTitle;
    }

    public PublicSite(Long publicSiteId, String creatorId, String name, String city, String location, String picture, String introduction, float avgCost, int capacity, int gameNum, String phone, Date uploadTime, int status, String type, String tag, float latitude, float longitude, String locationTitle) {
        this.publicSiteId = publicSiteId;
        this.creatorId = creatorId;
        this.name = name;
        this.city = city;
        this.location = location;
        this.picture = picture;
        this.introduction = introduction;
        this.avgCost = avgCost;
        this.capacity = capacity;
        this.gameNum = gameNum;
        this.phone = phone;
        this.uploadTime = uploadTime;
        this.status = status;
        this.type = type;
        this.tag = tag;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationTitle = locationTitle;
    }
}
