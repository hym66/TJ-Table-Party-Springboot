package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.service.ClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Club"})
@RestController
@RequestMapping("club")
public class ClubController {
    @Autowired
    private ClubService clubService;

    @ApiOperation("根据俱乐部id返回俱乐部详情")
    @GetMapping("getClubDetail")
    public Result<Club> getClubDetail(@ApiParam(name="clubId", value="俱乐部id", required = true)
                                                  @RequestParam("clubId") Long clubId)
    {
        Club club = clubService.selectClub(clubId);
        return Result.success(club);
    }

    @ApiOperation("根据俱乐部id返回俱乐部详情")
    @PostMapping("postOneNewClub")
    public Result<Long> postOneNewClub(@RequestBody Club club)
    {
        Long newID = clubService.insertOneNewClub(club);
        return Result.success(newID);
    }
}
