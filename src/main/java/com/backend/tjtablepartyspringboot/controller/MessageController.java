package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.entity.Message;
import com.backend.tjtablepartyspringboot.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("根据用户ID获取所有消息")
    @GetMapping("getUserAllMessage")
    public Result<List<MessageDto>> getUserAllMessage(@ApiParam(name = "userId", value = "用户id", required = true)
                                                      @RequestParam("userId") Long userId) {
        return Result.success(messageService.selectMessageInfoByUserId(userId));
    }

    @ApiOperation("根据消息ID获取消息")
    @GetMapping("getMessageById")
    public Result<Message> getMessageById(@ApiParam(name = "messageId", value = "消息id", required = true)
                                          @RequestParam("messageId") Long messageId) {
        return Result.success(messageService.selectMessageInfoById(messageId));
    }

    @ApiOperation("用户删除消息")
    @DeleteMapping("deleteUserViewMessage")
    public Result<String> deleteUserViewMessage(@ApiParam(name = "userId", value = "用户id", required = true)
                                                 @RequestParam("userId") Long userId,
                                                 @ApiParam(name = "messageId", value = "消息id", required = true)
                                                 @RequestParam("messageId") Long messageId) {
        int res = messageService.deleteUserViewMessage(userId, messageId);
        if (res == 1) return Result.success("删除成功");
        else return Result.success("删除失败");
    }
}
