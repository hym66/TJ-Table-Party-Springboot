package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.Announce;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.service.ClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Club"})
@RestController
@RequestMapping("club")
public class ClubController {
    @Autowired
    ClubService clubService;

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
    public Result<Long> postOneNewClub(@RequestBody Club club)
    {
        Long newID = clubService.insertOneNewClub(club);
        return Result.success(newID);
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
                                                        @RequestParam("userId") Long userId)

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


}
