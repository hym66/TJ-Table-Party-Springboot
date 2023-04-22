package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.ClubAnnounceDto;
import com.backend.tjtablepartyspringboot.dto.ClubInfoDetailDto;
import com.backend.tjtablepartyspringboot.dto.ClubRecordDetailDto;
import com.backend.tjtablepartyspringboot.dto.ClubUserDetailDto;
import com.backend.tjtablepartyspringboot.entity.Announce;
import com.backend.tjtablepartyspringboot.entity.Club;
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
}
