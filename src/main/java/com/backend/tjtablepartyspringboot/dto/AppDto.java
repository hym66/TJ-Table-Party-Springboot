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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long publicSiteId;
    @JsonSerialize(using= ToStringSerializer.class)
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
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date uploadTime;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date checkTime;
    String[] type;
    String[] tag;
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
        if(publicSite.getTag() != null) {
            this.tag = publicSite.getTag().split(",");
        }
        this.openTime = openTime;
    }
}
