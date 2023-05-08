package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.Announce;
import com.backend.tjtablepartyspringboot.entity.Club;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClubService {
    ClubInfoDetailDto selectClubInfo(Long clubId);
    ClubUserDetailDto selectClubUser(Long clubId);
    ClubRecordDetailDto selectClubRecord(Long clubId);
    Long insertOneNewClub(Club club);
    int addAnnounce(Announce announce);
    List<ClubAnnounceDto> getClubAnnounceDtos(Long clubId);
    List<ClubSimpleDto> getCityClubSimpleDtos(String city, float longitude, float latitude);
    List<ClubSimpleDto> getUserClubSimpleDtos(String userId);
    int removeClubTrpg(Long clubId, Long trpgId);
    int addClubTrpg(Long clubId, Long trpgId);
    List<Activity> selectCurrentActivities(Long clubId);
    int patchClub(Club club);
    int addUser(Long clubId, String userId);
    int removeUser(Long clubId, String userId);
}
