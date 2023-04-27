package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.ClubService;
import com.backend.tjtablepartyspringboot.util.TimeUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    ClubMapper clubMapper;
    @Autowired
    AnnounceMapper announceMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ClubTrpgMapper clubTrpgMapper;
    @Autowired
    ClubUserMapper clubUserMapper;
    @Autowired
    ClubRecordMapper clubRecordMapper;


    @Override
    public ClubInfoDetailDto selectClubInfo(Long clubId) {
        //从club表检索基本信息
        Club club = clubMapper.selectById(clubId);

        //从announce表检索公告
        List<Announce> announceList = announceMapper.selectByClubId(clubId);
        List<ClubAnnounceDto> clubAnnounceDtoList = new ArrayList<>();

        //todo：给announce加人名和头像
        for(Announce a : announceList){
            Long uid = a.getAnnounceUserId();
            //查询人名和头像
            //...
            String name = "查询出来的姓名";
            String avatar = "http://www.baidu.com/img/bdlogo.png";
            ClubAnnounceDto dto = new ClubAnnounceDto(a, name, avatar);
            clubAnnounceDtoList.add(dto);
        }

        //从activity表检索活动
        List<Activity> pastActivityList = activityMapper.selectPastByClubId(clubId);

        //从club_trpg表检索游戏
        List<ClubTrpg> clubTrpgList = clubTrpgMapper.selectByClubId(clubId);

        //合成dto
        ClubInfoDetailDto clubInfoDetailDto = new ClubInfoDetailDto(club, clubAnnounceDtoList, pastActivityList, clubTrpgList);

        return clubInfoDetailDto;
    }

    @Override
    public ClubUserDetailDto selectClubUser(Long clubId) {
        Club club = clubMapper.selectById(clubId);
        Long managerId = club.getManagerId();

        List<ClubUser> clubUserList = clubUserMapper.selectUsersByClubId(clubId);//实体类列表
        List<ClubUserDto> clubUserDtoList = new ArrayList<>();//dto列表

        for(ClubUser u : clubUserList){
            //把role属性加上
            String role = u.getUserId() == managerId ? "manager" : "member";
            //todo：查询name和avatar
            String name = "查询到的名字";
            String avatar = "//oss.gstonegames.com/static/image/gameimgEh/pic17940221543461645613.jpg";

            ClubUserDto clubUserDto = new ClubUserDto(u.getClubId(), u.getUserId(), role, name, avatar);
            clubUserDtoList.add(clubUserDto);
        }

        ClubUserDetailDto clubUserDetailDto = new ClubUserDetailDto(clubUserDtoList);
        return clubUserDetailDto;
    }

    @Override
    public ClubRecordDetailDto selectClubRecord(Long clubId) {
        List<ClubRecord> clubRecordList = clubRecordMapper.selectRecordsByClubId(clubId);
        ClubRecordDetailDto clubRecordDetailDto = new ClubRecordDetailDto(clubRecordList);
        return clubRecordDetailDto;
    }


    @Override
    public Long insertOneNewClub(Club club) {
        Long newID = Long.valueOf(clubMapper.insert(club));
        return newID;
    }

    @Override
    public int addAnnounce(Announce announce) {
        announce.setAnnouncePubTime(new Date());
        int res = announceMapper.insert(announce);
        return res;
    }

    @Override
    public List<ClubAnnounceDto> getClubAnnounceDtos(Long clubId) {
        List<Announce> announceList = announceMapper.selectByClubId(clubId);
        announceList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());//把所有公告按时间降序

        List<ClubAnnounceDto> clubAnnounceDtoList = new ArrayList<>();
        for(Announce a : announceList){
            //todo：补用户名和头像
            String name = "查询出来的姓名";
            String avatar = "http://www.baidu.com/img/bdlogo.png";

            ClubAnnounceDto dto = new ClubAnnounceDto(a, name, avatar);
            clubAnnounceDtoList.add(dto);
        }

        return clubAnnounceDtoList;
    }

    @Override
    public List<ClubSimpleDto> getCityClubSimpleDtos(String city, float longitude, float latitude) {
        List<Club> clubList = clubMapper.selectByCity(city);
        //todo:按经纬度排序
        //...
        List<ClubSimpleDto> clubSimpleDtoList = new ArrayList<>();
        //查询currentPersons, managerAvatar, managerName
        for(Club c : clubList){
            Long clubId = c.getClubId();
            Long managerId = c.getManagerId();

            int currentPersons = clubMapper.selectClubPersonNum(clubId);
            //todo:user表查询
            String managerAvatar = "http://www.baidu.com/img/bdlogo.png";
            String managerName = "查询出来的姓名";

            ClubSimpleDto dto = new ClubSimpleDto(c, currentPersons, managerName, managerAvatar);
            clubSimpleDtoList.add(dto);
        }

        return clubSimpleDtoList;
    }

    @Override
    public List<ClubSimpleDto> getUserClubSimpleDtos(Long userId) {
        List<Club> clubList = clubMapper.selectUserClubs(userId);
        List<ClubSimpleDto> clubSimpleDtoList = new ArrayList<>();
        //查询currentPersons, managerAvatar, managerName
        for(Club c : clubList){
            Long clubId = c.getClubId();
            Long managerId = c.getManagerId();

            int currentPersons = clubMapper.selectClubPersonNum(clubId);
            //todo:user表查询
            String managerAvatar = "http://www.baidu.com/img/bdlogo.png";
            String managerName = "查询出来的姓名";

            ClubSimpleDto dto = new ClubSimpleDto(c, currentPersons, managerName, managerAvatar);
            clubSimpleDtoList.add(dto);
        }
        return clubSimpleDtoList;
    }
}
