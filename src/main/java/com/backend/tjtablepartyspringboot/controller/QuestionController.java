package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.Question;
import com.backend.tjtablepartyspringboot.entity.Reply;
import com.backend.tjtablepartyspringboot.entity.UserLikeQuestion;
import com.backend.tjtablepartyspringboot.entity.UserLikeReply;
import com.backend.tjtablepartyspringboot.service.QuestionService;
import com.backend.tjtablepartyspringboot.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/9:45 PM
 * @Description:
 */
@Api(tags = {"Question"})
@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    ReplyService replyService;

    @Autowired
    QuestionService questionService;

    @ApiOperation("获取该question对应的reply信息列表")
    @GetMapping("/getReplyList")
    public Result<Map<String,Object>> getReplyList(
            @ApiParam(name = "questionId", value = "问题id", required = true)
            @RequestParam("questionId") Long questionId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name="userId",defaultValue = "1") Long userId
    ){
        Map<String,Object> resultMap=new HashMap<>();
        try
        {
            resultMap =questionService.getReplyList(questionId,userId);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("question的UserLike")
    @GetMapping("/getUserLikeQuestionList")
    public Result<Map<String,Object>> getUserLikeQuestionList(
            @ApiParam(name = "questionId", value = "问题id", required = true)
            @RequestParam("questionId") Long questionId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            List<UserLikeQuestion> list =questionService.getUserLikeQuestionList(questionId);
            resultMap.put("data",list);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("reply的UserLike")
    @GetMapping("/getUserLikeReplyList")
    public Result<Map<String,Object>> getUserLikeReplyList(
            @ApiParam(name = "replyId", value = "回答id", required = true)
            @RequestParam("replyId") Long replyId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            List<UserLikeReply> list =questionService.getUserLikeReplyList(replyId);
            resultMap.put("data",list);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


}
