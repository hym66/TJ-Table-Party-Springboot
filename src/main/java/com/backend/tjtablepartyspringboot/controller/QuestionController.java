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
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(name="userId") Long userId
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



    @ApiOperation("发布新的reply")
    @PostMapping("/postReply")
    public Result<Map<String,Object>> postReply(
            @ApiParam(name = "replyData", value = "reply字典", required = true)
            @RequestBody(required = true) Map<String, String>replyData

    ){
        Map<String,Object>resultMap=new HashMap<>();

        try
        {
            Long questionId=Long.parseLong(replyData.get("questionId"));
            Long userId=Long.parseLong(replyData.get("userId"));
            String content=replyData.get("content");
            String anonymity=replyData.get("anonymity");

            Integer i =questionService.addReply(questionId,userId,content,anonymity);
            resultMap.put("i",i);

            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }


    }



    @ApiOperation("发布新的question")
    @PostMapping("/postQuestion")
    public Result<Map<String,Object>> postQuestion(
            @ApiParam(name = "questionData", value = "question字典", required = true)
            @RequestBody(required = true) Map<String, String>questionData

    ){
        Map<String,Object>resultMap=new HashMap<>();

        try
        {
            Long userId=Long.parseLong(questionData.get("userId"));
            Long activityId=Long.parseLong(questionData.get("activityId"));
            String content=questionData.get("content");
            String title=questionData.get("title");
            String anonymity=questionData.get("anonymity");

            Integer i =questionService.addQuestion(activityId,userId,content,title,anonymity);
            resultMap.put("i",i);

            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }


    }



    @ApiOperation("user 点赞/取消点赞一条reply")
    @PostMapping("/likeReply")
    public Result<Map<String,Object>> likeReply(
            @ApiParam(name = "userLikeReplyMap", value = "userLikeReply字典", required = true)
            @RequestBody(required = true) Map<String, String>userLikeReplyMap
    ){
        Map<String,Object>resultMap=new HashMap<>();

        try
        {
            Long replyId=Long.parseLong(userLikeReplyMap.get("replyId"));
            Long userId=Long.parseLong(userLikeReplyMap.get("userId"));

            Integer i =questionService.userLikeOneReply(userId,replyId);
            resultMap.put("i",i);

            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }


    }


    @ApiOperation("user 点赞/取消点赞一条 question")
    @PostMapping("/likeQuestion")
    public Result<Map<String,Object>> likeQuestion(
            @ApiParam(name = "userLikeQuestionMap", value = "userLikeReply字典", required = true)
            @RequestBody(required = true) Map<String, String>userLikeQuestionMap
    ){
        Map<String,Object>resultMap=new HashMap<>();

        try
        {
            Long questionId=Long.parseLong(userLikeQuestionMap.get("questionId"));
            Long userId=Long.parseLong(userLikeQuestionMap.get("userId"));

            Integer i =questionService.userLikeOneQuestion(userId,questionId);
            resultMap.put("i",i);

            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }


    }


    @ApiOperation("删除一条reply")
    @DeleteMapping("/deleteReply")
    public Result<Map<String,Object>> deleteReply(
            @ApiParam(name = "replyId", value = "回答id", required = true)
            @RequestParam("replyId") Long replyId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i =questionService.deleteReply(replyId);
            resultMap.put("i",i);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }


    }


    @ApiOperation("删除一条 question")
    @DeleteMapping("/deleteQuestion")
    public Result<Map<String,Object>> deleteQuestion(
            @ApiParam(name = "questionId", value = "问题id", required = true)
            @RequestParam("questionId") Long questionId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i =questionService.deleteQuestion(questionId);
            resultMap.put("i",i);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }


    }



}
