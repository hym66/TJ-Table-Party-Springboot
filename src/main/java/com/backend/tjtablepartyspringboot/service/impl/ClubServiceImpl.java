package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.ClubService;
import com.backend.tjtablepartyspringboot.util.TimeUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.accessibility.AccessibleIcon;
import java.util.*;
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

    @Autowired
    UserMapper userMapper;


    @Override
    public ClubInfoDetailDto selectClubInfo(Long clubId) {
        //从club表检索基本信息
        Club club = clubMapper.selectById(clubId);

        //从announce表检索公告
        List<Announce> announceList = announceMapper.selectByClubId(clubId);
        List<ClubAnnounceDto> clubAnnounceDtoList = new ArrayList<>();

        for(Announce a : announceList){
            String uid = a.getAnnounceUserId();
            //查询人名和头像
            User user = userMapper.selectById(uid);
            String name = user.getNickName();
            String avatar = user.getAvatarUrl();

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
        String managerId = club.getManagerId();

        List<ClubUser> clubUserList = clubUserMapper.selectUsersByClubId(clubId);//实体类列表
        List<ClubUserDto> clubUserDtoList = new ArrayList<>();//dto列表

        for(ClubUser u : clubUserList){
            //把role属性加上
            String role = u.getUserId().equals(managerId) ? "manager" : "member";

            User user = userMapper.selectById(u.getUserId());
            String name = user.getNickName();
            String avatar = user.getAvatarUrl();

            ClubUserDto clubUserDto = new ClubUserDto(u.getClubId(), u.getUserId(), role, name, avatar, u.getStatus());
            clubUserDtoList.add(clubUserDto);
        }

        ClubUserDetailDto clubUserDetailDto = new ClubUserDetailDto(clubUserDtoList);
        return clubUserDetailDto;
    }

    @Override
    public ClubUserDetailDto selectWaitingClubUser(Long clubId) {
        Club club = clubMapper.selectById(clubId);
        String managerId = club.getManagerId();

        List<ClubUser> clubUserList = clubUserMapper.selectWaitingUsers(clubId);//实体类列表
        List<ClubUserDto> clubUserDtoList = new ArrayList<>();//dto列表

        for(ClubUser u : clubUserList){
            //把role属性加上
            String role = u.getUserId() == managerId ? "manager" : "member";

            User user = userMapper.selectById(u.getUserId());
            String name = user.getNickName();
            String avatar = user.getAvatarUrl();

            ClubUserDto clubUserDto = new ClubUserDto(u.getClubId(), u.getUserId(), role, name, avatar, u.getStatus());
            clubUserDtoList.add(clubUserDto);
        }

        ClubUserDetailDto clubUserDetailDto = new ClubUserDetailDto(clubUserDtoList);
        return clubUserDetailDto;
    }

    @Override
    public ClubRecordDetailDto selectClubRecord(Long clubId) {
        List<ClubRecord> clubRecordList = clubRecordMapper.selectRecordsByClubId(clubId);
        //按时间降序排序
        Collections.reverse(clubRecordList);

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

            User user = userMapper.selectById(a.getAnnounceUserId());
            String name = user.getNickName();
            String avatar = user.getAvatarUrl();

            ClubAnnounceDto dto = new ClubAnnounceDto(a, name, avatar);
            clubAnnounceDtoList.add(dto);
        }

        return clubAnnounceDtoList;
    }

    @Override
    public List<ClubSimpleDto> getCityClubSimpleDtos(String city, float longitude, float latitude) {
        List<Club> clubList = clubMapper.selectByCity(city);

        List<ClubSimpleDto> clubSimpleDtoList = new ArrayList<>();
        //查询currentPersons, managerAvatar, managerName
        for(Club c : clubList){
            Long clubId = c.getClubId();
            String managerId = c.getManagerId();

            int currentPersons = clubMapper.selectClubPersonNum(clubId);

            User user = userMapper.selectById(managerId);
            String managerName = user.getNickName();
            String managerAvatar = user.getAvatarUrl();

            ClubSimpleDto dto = new ClubSimpleDto(c, currentPersons, managerName, managerAvatar);
            clubSimpleDtoList.add(dto);
        }

        return clubSimpleDtoList;
    }

    @Override
    public List<ClubSimpleDto> getUserClubSimpleDtos(String userId) {
        List<Club> clubList = clubMapper.selectUserClubs(userId);
        List<ClubSimpleDto> clubSimpleDtoList = new ArrayList<>();
        //查询currentPersons, managerAvatar, managerName
        for(Club c : clubList){
            Long clubId = c.getClubId();
            String managerId = c.getManagerId();

            int currentPersons = clubMapper.selectClubPersonNum(clubId);

            User user = userMapper.selectById(managerId);
            String managerName = user.getNickName();
            String managerAvatar = user.getAvatarUrl();

            ClubSimpleDto dto = new ClubSimpleDto(c, currentPersons, managerName, managerAvatar);
            clubSimpleDtoList.add(dto);
        }
        return clubSimpleDtoList;
    }

    @Override
    public int removeClubTrpg(Long clubId, String trpgId) {
        int res = clubMapper.deleteClubTrpg(clubId, trpgId);
        return res;
    }

    @Override
    public int askToJoin(Long clubId, String userId) {
        ClubUser clubUser = new ClubUser(userId, clubId, Byte.valueOf("0"));
        int res = clubUserMapper.insert(clubUser);
        return res;
    }

    @Override
    public int agreeToJoin(Long clubId, String userId){
        int res = clubUserMapper.activateClubUser(clubId, userId);
        return res;
    }

    @Override
    public int addClubTrpg(Long clubId, String trpgId, String title, String poster) {
        int res = clubMapper.addClubTrpg(clubId, trpgId, title, poster);
        return res;
    }

    @Override
    public List<Activity> selectCurrentActivities(Long clubId) {
        List<Activity> activityList = activityMapper.selectCurrentByClubId(clubId);
        return activityList;
    }

    @Override
    public int patchClub(Club club) {
        int res = clubMapper.updateById(club);
        return res;
    }

    @Override
    public int removeUser(Long clubId, String userId) {
        int res = clubUserMapper.deleteUser(clubId, userId);
        return res;
    }

    @Override
    public int addRecord(Long clubId, String content) {
        ClubRecord clubRecord = new ClubRecord();
        clubRecord.setClubId(clubId);
        clubRecord.setContent(content);

        clubRecord.setTime(new Date());

        int res = clubRecordMapper.insert(clubRecord);
        return res;
    }

    @Override
    public boolean clubIsFull(Long clubId) {
        int currentPersons = clubMapper.selectClubPersonNum(clubId);
        Club club = clubMapper.selectById(clubId);
        int capacity = club.getCapacity();
        return currentPersons >= capacity;
    }

    @Override
    public int dissolveClub(Long clubId) {
        int res = clubMapper.deleteById(clubId);
        return res;
    }

    @Override
    public int transferManager(Long clubId, String userId) {
        Club club = clubMapper.selectById(clubId);

        String oldManagerId = club.getManagerId();
        club.setManagerId(userId);
        int res = clubMapper.updateById(club);

        User oldManager = userMapper.selectById(oldManagerId);
        User newManager = userMapper.selectById(userId);

        res = addRecord(clubId, oldManager.getNickName()+"将部长职位转让给了"+newManager.getNickName());

        return res;
    }

    @Override
    public boolean isInThisClub(Long clubId, String userId) {
        ClubUser clubUser = clubUserMapper.selectClubUserById(clubId, userId);
        return clubUser != null && clubUser.getStatus() == 1;
    }

    @Override
    public int deleteAnnounce(Long announceId) {
        int res = announceMapper.deleteById(announceId);
        return res;
    }

    @Override
    public List<ClubUser> getClubUsers(Long clubId) {
        List<ClubUser> clubUserList = clubUserMapper.selectUsersByClubId(clubId);
        return clubUserList;
    }

    @Override
    public ClubSimpleDto getClubSimpleDto(Long clubId) {
        Club club = clubMapper.selectById(clubId);

        //这里的clubSimpleDto信息不全
        ClubSimpleDto clubSimpleDto = new ClubSimpleDto(club);

        return clubSimpleDto;
    }

    @Override
    public List<ClubSimpleDto> selectByKeyword(String keyword) {
        List<Club> clubList = clubMapper.selectByKeyword(keyword);
        List<ClubSimpleDto> dtoList = new ArrayList<>();
        for(Club c : clubList){
            Long clubId = c.getClubId();
            int currentPersons = clubMapper.selectClubPersonNum(clubId);

            User user = userMapper.selectById(c.getManagerId());
            String managerName = user.getNickName();
            String managerAvatar = user.getAvatarUrl();
            ClubSimpleDto dto = new ClubSimpleDto(c, currentPersons, managerName, managerAvatar);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
