package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.Club;
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
public class ClubSimpleDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    String posterUrl;
    String clubTitle;
    String mainTime;
    String meetingPoint;
    String city;
    int capacity;
    int currentPersons;
    @JsonSerialize(using= ToStringSerializer.class)
    Long managerId;
    String managerAvatar;
    String managerName;

    public ClubSimpleDto(Club club, int currentPersons, String managerName, String managerAvatar){
        this.clubId = club.getClubId();
        this.posterUrl = club.getPosterUrl();
        this.clubTitle = club.getClubTitle();
        this.mainTime = club.getMainTime();
        this.meetingPoint = club.getMeetingPoint();
        this.city = club.getCity();
        this.capacity = club.getCapacity();
        this.currentPersons = currentPersons;
        this.managerId = club.getManagerId();
        this.managerAvatar = managerAvatar;
        this.managerName = managerName;
    }
}
