package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Api(tags = {"Message"})
@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @ApiOperation("根据用户ID获取所有信息")
    @GetMapping("getPublicSiteList")
    public Result<List<MessageDto>> getUserAllMessage(@ApiParam(name = "userId", value = "用户id", required = true)
                                                      @RequestParam("userId") Long userId) {
        return Result.success(messageService.selectMessageInfoByUserId(userId));
    }
}
