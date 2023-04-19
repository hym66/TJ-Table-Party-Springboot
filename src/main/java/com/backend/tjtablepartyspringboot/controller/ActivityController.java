package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.service.ActivityService;
import com.backend.tjtablepartyspringboot.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @Author: 杨严
 * @Date: 2023/04/19/8:40 PM
 * @Description:
 */
@Api(tags = {"Activity"})
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @Autowired
    QuestionService questionService;


    @ApiOperation("获取所有activity实体")
    @GetMapping("/getAllActivity")
    public Result<List<Activity>> getAllActivity()
    {
        List<Activity> list=activityService.getAllEntity();

        return Result.success(list);
    }

    @ApiOperation("根据activity id获取activity")
    @GetMapping("/getByActivityId")
    public Result<Map<String,Object>>getByActivityId(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Activity activity=activityService.getEntityByActivityId(activityId);
            resultMap.put("data",activity);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("获取该活动对应的所有trpg信息列表")
    @GetMapping("/getTrpgList")
    public Result<Map<String,Object>>getTrpgList(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Map<String,Object> map=activityService.getActivityHasTrpgEntity(activityId);
            resultMap.put("data",map);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


    @ApiOperation("获取该活动对应的所有question信息列表")
    @GetMapping("/getQuestionList")
    public Result<Map<String,Object>>getQuestionList(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            //获取question 列表，但是只有user id
            List<Question>list =questionService.getListByActivityId(activityId);

            resultMap.put("data",list);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("所有喜欢这个活动的user的信息列表")
    @GetMapping("/getUserInterestList")
    public Result<Map<String,Object>>getUserInterestList(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            //获取UserInterestActivity，只有user 的id
            List<UserInterestActivity>list =activityService.getUserInterestActivityList(activityId);
            //查user表，获取user具体信息


            resultMap.put("data",list);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("所有参与这个活动的user的信息列表")
    @GetMapping("/getUserJoinList")
    public Result<Map<String,Object>>getUserJoinList(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            //获取UserInterestActivity，只有user 的id
            List<UserJoinActivity>list =activityService.getUserJoinActivityList(activityId);
            //查user表，获取user具体信息


            resultMap.put("data",list);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }



//    @ApiOperation("")
//    @GetMapping("/getByActivityId")
//    public Result<Map<String,Object>>getByActivityId(
//            @ApiParam(name = "activityId", value = "活动id", required = true)
//            @RequestParam("activityId") Long activityId
//    ){
//        Map<String,Object>resultMap=new HashMap<>();
//        try
//        {
//            Activity activity=activityService.getEntityByActivityId(activityId);
//            resultMap.put("data",activity);
//            return Result.success(resultMap);
//        }catch (Exception e){
//            return Result.fail(0,e.getMessage());
//        }
//
//    }

}
