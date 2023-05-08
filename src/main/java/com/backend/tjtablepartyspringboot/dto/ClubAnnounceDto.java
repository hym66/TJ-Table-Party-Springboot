package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.Announce;
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
public class ClubAnnounceDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long announceId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    String announceContent;
    @JsonSerialize(using= ToStringSerializer.class)
    String announceUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date announcePubTime;

    //人名和头像是另外从user表查出来的
    String announceUserName;
    String avatar;

    public ClubAnnounceDto(Announce announce, String announceUserName, String avatar){
        this.announceId = announce.getAnnounceId();
        this.clubId = announce.getClubId();
        this.announceContent = announce.getAnnounceContent();
        this.announceUserId = announce.getAnnounceUserId();
        this.announcePubTime = announce.getAnnouncePubTime();
        this.announceUserName = announceUserName;
        this.avatar = avatar;
    }
}
