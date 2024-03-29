package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.dto.TrpgWaitingDetailDto;
import com.backend.tjtablepartyspringboot.dto.TrpgWaitingSimpleDto;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.backend.tjtablepartyspringboot.mapper.TrpgPublicMapper;
import com.backend.tjtablepartyspringboot.service.CrawlTrpgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"CrawlTrpg"})
@RestController
@RequestMapping("crawlTrpg")
public class CrawlTrpgController {
    @Autowired
    CrawlTrpgService crawlTrpgService;

    @Autowired
    TrpgPublicMapper trpgPublicMapper;

    @ApiOperation("获取所有待审核的游戏")
    @GetMapping("getAllTrpgWaiting")
    public Result<List<TrpgWaitingSimpleDto>> getAllTrpgWaiting() {
        List<TrpgWaitingSimpleDto> dtoList = crawlTrpgService.findAll();
        return Result.success(dtoList);
    }

    @ApiOperation("根据id获取一个待审核的游戏")
    @GetMapping("getTrpgWaitingById")
    public Result<TrpgWaitingDetailDto> getTrpgWaitingById(@ApiParam(name="trpgId", value="桌游id", required = true)
                                                            @RequestParam("trpgId") String trpgId) {
        TrpgPublicWaiting t = crawlTrpgService.findById(trpgId);
        TrpgWaitingDetailDto dto = new TrpgWaitingDetailDto(t);
        return Result.success(dto);
    }

    @ApiOperation("根据游戏id，将游戏入公共库")
    @GetMapping("addTrpgPublic")
    @Transactional
    public Result<String> addTrpgPublic(@ApiParam(name="trpgId", value="桌游id", required = true)
                                        @RequestParam("trpgId") String trpgId){
        try {
            TrpgPublic trpgPublic = trpgPublicMapper.selectById(trpgId);
            //如果这个游戏已经在库中了，就不再重复添加了
            if(trpgPublic == null) {
                int res = crawlTrpgService.addTrpgPublic(trpgId);
            }

            int res = crawlTrpgService.removeTrpgPublic(trpgId);
            return Result.success("游戏入库成功！");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(500, "游戏入库失败");
        }
    }

    @ApiOperation("根据游戏id，拒绝游戏入库")
    @DeleteMapping("refuseTrpgPublic")
    public Result<String> refuseTrpgPublic(@ApiParam(name="trpgId", value="桌游id", required = true)
                                        @RequestParam("trpgId") String trpgId){
        int res = crawlTrpgService.refuseTrpgPublic(trpgId);
        return Result.success("已拒绝游戏入库！");
    }
}
