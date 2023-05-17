package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.entity.Message;
import com.backend.tjtablepartyspringboot.entity.PrivateSite;
import com.backend.tjtablepartyspringboot.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
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
                                                      @RequestParam("userId") String userId) {
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
                                                @RequestParam("userId") String userId,
                                                @ApiParam(name = "messageId", value = "消息id", required = true)
                                                @RequestParam("messageId") Long messageId) {
        int res = messageService.deleteUserViewMessage(userId, messageId);
        if (res == 1) return Result.success("删除成功");
        else return Result.success("删除失败");
    }

    @ApiOperation("用户浏览消息更新消息状态")
    @PutMapping("updateMessageView")
    public Result<String> updateMessageView(@ApiParam(name = "userId", value = "用户id", required = true)
                                            @RequestParam("userId") String userId,
                                            @ApiParam(name = "messageId", value = "消息id", required = true)
                                            @RequestParam("messageId") Long messageId) {
        int res = messageService.updateMessageView(userId, messageId);
        if (res == 1) return Result.success("消息状态更新成功");
        else return Result.success("消息状态更新失败");
    }

    @ApiOperation("添加消息")
    @PostMapping("sendMessageView")
    public int addUserMessage(@RequestBody HashMap<String, String> map) {
        String userId = map.get("userId");
        Long sourceId = Long.valueOf(map.get("sourceId"));
        String title = map.get("title");
        String content = map.get("content");
        Date time = new Date();
        int type = Integer.parseInt(map.get("type"));
        Message message = new Message(sourceId, title, content, time, type);
        return messageService.sendMessage(userId, message);
    }

}
