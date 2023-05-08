package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.Announce;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.mapper.ClubMapper;
import com.backend.tjtablepartyspringboot.service.ClubService;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import com.backend.tjtablepartyspringboot.util.GeoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = {"Club"})
@RestController
@RequestMapping("club")
public class ClubController {
    @Autowired
    ClubService clubService;

    @Autowired
    ClubMapper clubMapper;

    @ApiOperation("根据俱乐部id，返回俱乐部基本信息")
    @GetMapping("getClubInfo")
    public Result<ClubInfoDetailDto> getClubInfo(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                  @RequestParam("clubId") Long clubId)
    {
        ClubInfoDetailDto clubDetail = clubService.selectClubInfo(clubId);
        return Result.success(clubDetail);
    }

    @ApiOperation("根据俱乐部id，返回俱乐部所有当前正在进行的活动")
    @GetMapping("getClubCurrentActivities")
    public Result<List<Activity>> getClubCurrentActivities(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                 @RequestParam("clubId") Long clubId)
    {
        List<Activity> activityList = clubService.selectCurrentActivities(clubId);
        return Result.success(activityList);
    }

    @ApiOperation("根据俱乐部id，返回俱所有俱乐部成员")
    @GetMapping("getClubUser")
    public Result<ClubUserDetailDto> getClubUser(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                           @RequestParam("clubId") Long clubId)
    {
        ClubUserDetailDto clubUserDetailDto = clubService.selectClubUser(clubId);
        return Result.success(clubUserDetailDto);
    }

    @ApiOperation("根据俱乐部id，返回俱所有俱乐部记录")
    @GetMapping("getClubRecord")
    public Result<ClubRecordDetailDto> getClubRecord(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                 @RequestParam("clubId") Long clubId)
    {
        ClubRecordDetailDto clubRecordDetailDto = clubService.selectClubRecord(clubId);
        return Result.success(clubRecordDetailDto);
    }

    @ApiOperation("创建新俱乐部")
    @PostMapping("postOneNewClub")
    public Result<Map<String, Long>> postOneNewClub(@RequestBody Club club)
    {
        //todo:多个CRUD回滚事务
        int res = Math.toIntExact(clubService.insertOneNewClub(club));
        //向club_user表中插入部长与俱乐部的联系
        res = clubService.addUser(club.getClubId(), club.getManagerId());

        Long clubId = club.getClubId();
        Map<String, Long> hashMap = new HashMap<>();
        hashMap.put("clubId", clubId);
        return Result.success(hashMap);
    }

    @ApiOperation("创建新俱乐部时上传海报")
    @PostMapping("postClubPoster")
    public Result<String> postClubPoster(@RequestParam("file") MultipartFile multipartFile, @RequestParam("clubId") Long clubId)
    {
        Club club = clubMapper.selectById(clubId);
        String url = FileUtil.uploadFile("/club/"+clubId.toString()+"/", multipartFile);

        club.setPosterUrl(url);
        int res = clubMapper.updateById(club);

        if(res > 0){
            return Result.success("插入俱乐部海报成功！");
        }
        else{
            return Result.fail(500, "插入俱乐部海报失败");
        }
    }

    @ApiOperation("给俱乐部添加一条公告")
    @PostMapping("addAnnounce")
    public Result<Announce> addAnnounce(@RequestBody Announce announce)
    {
        int res = clubService.addAnnounce(announce);
        if(res > 0){
            return Result.success(announce);
        }
        else{
            return Result.fail(500,"添加公告失败！请检查后端！");
        }
    }

    @ApiOperation("根据俱乐部id，获取所有公告")
    @GetMapping("getClubAnnounces")
    public Result<List<ClubAnnounceDto>> getClubAnnounces(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                       @RequestParam("clubId") Long clubId)
    {
        List<ClubAnnounceDto> clubAnnounceDtoList = clubService.getClubAnnounceDtos(clubId);
        return Result.success(clubAnnounceDtoList);
    }

    @ApiOperation("返回同城俱乐部，按距离排序（但是按距离排序还没做）")
    @GetMapping("getCityClubs")
    public Result<List<ClubSimpleDto>> getCityClubs(@ApiParam(name="city", value="城市", required = true)
                                                          @RequestParam("city") String city,
                                                    @ApiParam(name="longitude", value="经度", required = true)
                                                            @RequestParam("longitude") Float longitude,
                                                    @ApiParam(name="latitude", value="纬度", required = true)
                                                            @RequestParam("latitude") Float latitude)
    {
        List<ClubSimpleDto> clubInfoDetailDtoList = clubService.getCityClubSimpleDtos(city, longitude, latitude);
        return Result.success(clubInfoDetailDtoList);
    }

    @ApiOperation("返回指定用户参与的俱乐部")
    @GetMapping("getUserClubs")
    public Result<List<ClubSimpleDto>> getUserClubs(@ApiParam(name="userId", value="用户id", required = true)
                                                        @RequestParam("userId") String userId)

    {
        List<ClubSimpleDto> clubInfoDetailDtoList = clubService.getUserClubSimpleDtos(userId);
        return Result.success(clubInfoDetailDtoList);
    }

    @ApiOperation("添加俱乐部游戏")
    @PostMapping("addClubTrpg")
    public Result<String> addClubTrpg(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                       @RequestParam("clubId") Long clubId,
                                       @ApiParam(name="trpgId", value="桌游id", required = true)
                                       @RequestParam("trpgId") Long trpgId)

    {
        int res = clubService.addClubTrpg(clubId, trpgId);
        if(res > 0){
            return Result.success("添加俱乐部游戏成功！");
        }
        else{
            return Result.fail(500,"添加失败，请检查");
        }
    }

    @ApiOperation("删除俱乐部游戏")
    @DeleteMapping("removeClubTrpg")
    public Result<String> getUserClubs(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                    @RequestParam("clubId") Long clubId,
                                                    @ApiParam(name="trpgId", value="桌游id", required = true)
                                                    @RequestParam("trpgId") Long trpgId)

    {
        int res = clubService.removeClubTrpg(clubId, trpgId);
        if(res > 0){
            return Result.success("删除俱乐部游戏成功！");
        }
        else{
            return Result.fail(500,"删除失败，请检查");
        }
    }

    @ApiOperation("修改俱乐部基本信息")
    @PostMapping("putClubInfo")
    public Result<String> putClubInfo(@RequestBody Club club)
    {
        int res = clubService.patchClub(club);
        return Result.success("更新俱乐部信息成功！");
    }

    @ApiOperation("俱乐部添加一名用户")
    @PostMapping("addUser")
    public Result<String> addUser(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                   @RequestParam("clubId") Long clubId,
                                               @ApiParam(name="userId", value="用户id", required = true)
                                                    @RequestParam("userId") String userId)

    {
        int res = clubService.addUser(clubId, userId);
        if(res > 0){
            return Result.success("添加成功！");
        }
        else{
            return Result.fail(500, "添加失败，请检查！");
        }
    }

    @ApiOperation("俱乐部删除一名用户")
    @DeleteMapping("removeUser")
    public Result<String> removeUser(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                  @RequestParam("clubId") Long clubId,
                                  @ApiParam(name="userId", value="用户id", required = true)
                                  @RequestParam("userId") String userId)

    {
        int res = clubService.removeUser(clubId, userId);
        if(res > 0){
            return Result.success("删除成功！");
        }
        else{
            return Result.fail(500, "删除失败，请检查！");
        }
    }

    @ApiOperation("俱乐部条件筛选")
    @PostMapping ("getConditionClubs")
    public Result<List<ClubSimpleDto>> getConditionClubs(@RequestBody ClubConditionDto clubConditionDto)
    {
        Set capacitySet = clubConditionDto.getCapacitySet();
        String city = clubConditionDto.getCity();
        String keyword = clubConditionDto.getKeyword();
        Float latitude = clubConditionDto.getLatitude();
        Float longitude = clubConditionDto.getLongitude();
        Float maxDistance = clubConditionDto.getMaxDistance();
        Float minDistance = clubConditionDto.getMinDistance();

        //1.关键词筛选
        List<ClubSimpleDto> clubList = clubService.selectByKeyword(keyword);

        //2.容量筛选
        clubList = clubList.stream()
                .filter((ClubSimpleDto c) -> capacitySet.contains(c.getCapacity()))
                .collect(Collectors.toList());

        //3.距离筛选
        if(maxDistance != null || minDistance != null) {
            clubList = clubList.stream()
                    .filter((ClubSimpleDto c) -> {
                        float dis = GeoUtil.getDistance(longitude, latitude, c.getLongitude(), c.getLatitude());
                        if (dis >= minDistance && dis <= maxDistance) {
                            return true;
                        } else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
        }

        return Result.success(clubList);
    }


}
