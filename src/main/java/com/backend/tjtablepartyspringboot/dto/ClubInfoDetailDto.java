package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.Announce;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.ClubTrpg;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubInfoDetailDto {
    Club club;

    //公告
    List<ClubAnnounceDto> announceList;

    //往期活动
    List<Activity> pastActivityList;

    //游戏
    List<ClubTrpg> clubTrpgList;

}
