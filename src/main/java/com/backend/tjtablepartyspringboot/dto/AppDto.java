package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long publicSiteId;
    @JsonSerialize(using= ToStringSerializer.class)
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
    String[] type;
    int status;
    List<PublicSiteTimeDto> openTime;

    public AppDto(PublicSite publicSite, String[] type, List<PublicSiteTimeDto> openTime){
        if(publicSite == null){
            return;
        }
        this.publicSiteId = publicSite.getPublicSiteId();
        this.creatorId = publicSite.getCreatorId();
        this.name = publicSite.getName();
        this.city = publicSite.getCity();
        this.introduction = publicSite.getIntroduction();
        this.phone = publicSite.getPhone();
        this.picture = publicSite.getPicture();
        this.city = publicSite.getCity();
        this.location = publicSite.getLocation();
        this.avgCost = publicSite.getAvgCost();
        this.capacity = publicSite.getCapacity();
        this.gameNum = publicSite.getGameNum();
        this.uploadTime = publicSite.getUploadTime();
        this.checkTime = publicSite.getCheckTime();
        this.status = publicSite.getStatus();

        this.type = type;
        this.openTime = openTime;
    }
}
