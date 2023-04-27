package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public AppSimpleDto(PublicSite publicSite){
        if(publicSite == null){
            return;
        }
        this.publicSiteId = publicSite.getPublicSiteId();
        this.name = publicSite.getName();
        this.picture = publicSite.getPicture();
        this.city = publicSite.getCity();
        this.location = publicSite.getLocation();
        this.type = publicSite.getType().split(",");
        this.avgCost = publicSite.getAvgCost();
        this.capacity = publicSite.getCapacity();
        this.gameNum = publicSite.getGameNum();
    }
}
