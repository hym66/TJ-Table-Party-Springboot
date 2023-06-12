package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppSimpleDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long publicSiteId;
    String name;
    String picture;
    String city;
    String location;
    String[] type;
    float avgCost;
    int capacity;
    int gameNum;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date uploadTime;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date checkTime;

    public AppSimpleDto(PublicSite publicSite){
        if(publicSite == null){
            return;
        }
        this.publicSiteId = publicSite.getPublicSiteId();
        this.name = publicSite.getName();
        this.picture = publicSite.getPicture();
        this.city = publicSite.getCity();
        this.location = publicSite.getLocation();
        this.type = publicSite.getType()==null ? null : publicSite.getType().split(",");
        this.avgCost = publicSite.getAvgCost();
        this.capacity = publicSite.getCapacity();
        this.gameNum = publicSite.getGameNum();
        this.uploadTime = publicSite.getUploadTime();
        this.checkTime = publicSite.getCheckTime();
    }
}
