package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.Announce;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.ClubUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClubService {
    ClubInfoDetailDto selectClubInfo(Long clubId);
    ClubUserDetailDto selectClubUser(Long clubId);
    ClubUserDetailDto selectWaitingClubUser(Long clubId);
    ClubRecordDetailDto selectClubRecord(Long clubId);
    Long insertOneNewClub(Club club);
    int addAnnounce(Announce announce);
    List<ClubAnnounceDto> getClubAnnounceDtos(Long clubId);
    List<ClubSimpleDto> getCityClubSimpleDtos(String city, float longitude, float latitude);
    List<ClubSimpleDto> getUserClubSimpleDtos(String userId);
    int removeClubTrpg(Long clubId, String trpgId);
    int addClubTrpg(Long clubId, String trpgId, String title, String poster);
    List<Activity> selectCurrentActivities(Long clubId);
    int patchClub(Club club);
    List<ClubSimpleDto> selectByKeyword(String keyword);
    int askToJoin(Long clubId, String userId);
    int agreeToJoin(Long clubId, String userId);
    int removeUser(Long clubId, String userId);
    int addRecord(Long clubId, String content);
    boolean clubIsFull(Long clubId);
    int dissolveClub(Long clubId);
    int transferManager(Long clubId, String userId);
    boolean isInThisClub(Long clubId, String userId);
    int deleteAnnounce(Long announceId);
    List<ClubUser> getClubUsers(Long clubId);
    ClubSimpleDto getClubSimpleDto(Long clubId);
}
