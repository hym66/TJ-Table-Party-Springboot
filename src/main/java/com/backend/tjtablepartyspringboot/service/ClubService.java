package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.ClubAnnounceDto;
import com.backend.tjtablepartyspringboot.dto.ClubInfoDetailDto;
import com.backend.tjtablepartyspringboot.dto.ClubRecordDetailDto;
import com.backend.tjtablepartyspringboot.dto.ClubUserDetailDto;
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
}
