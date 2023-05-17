package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.dto.TrpgWaitingSimpleDto;
import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.backend.tjtablepartyspringboot.service.CrawlTrpgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"CrawlTrpg"})
@RestController
@RequestMapping("crawlTrpg")
public class CrawlTrpgController {
    @Autowired
    CrawlTrpgService crawlTrpgService;

    @ApiOperation("根据用户ID获取所有消息")
    @GetMapping("getAllTrpgWaiting")
    public Result<List<TrpgWaitingSimpleDto>> getAllTrpgWaiting() {
        List<TrpgWaitingSimpleDto> dtoList = crawlTrpgService.findAll();
        return Result.success(dtoList);
    }

    @ApiOperation("根据id获取一个待审核的游戏")
    @GetMapping("getTrpgWaitingById")
    public Result<TrpgPublicWaiting> getTrpgWaitingById(@ApiParam(name="trpgId", value="桌游id", required = true)
                                                            @RequestParam("trpgId") String trpgId) {
        TrpgPublicWaiting dto = crawlTrpgService.findById(trpgId);
        return Result.success(dto);
    }
}
