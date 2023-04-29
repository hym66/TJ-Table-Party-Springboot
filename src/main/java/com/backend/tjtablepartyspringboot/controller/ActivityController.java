package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.service.ActivityService;
import com.backend.tjtablepartyspringboot.service.QuestionService;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.PublicKey;
import java.util.ArrayList;
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

    @ApiOperation("根据activity id获取activity entity")
    @GetMapping("/getEntity")
    public Result<Map<String,Object>>getEntity(
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
    @ApiOperation("根据activity id,获取activity detail详细信息")
    @GetMapping("/getDetail")
    public Result<Map<String,Object>>getDetail(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.getDetail(activityId);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }



    @ApiOperation("获取该活动对应的所有question信息列表")
    @GetMapping("/getQuestionList")
    public Result<List<Map<String,Object>>>getQuestionList(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name ="userId") Long userId

    ){
        List<Map<String,Object>>list=new ArrayList<>();
        try
        {

            list =questionService.getQuestionList(activityId,userId);

            return Result.success(list);
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

    @ApiOperation("输入 筛选参数、排序参数，分页返回list")
    @GetMapping("/getList")
    public Result<Map<String,Object>> getList(
            @ApiParam(name = "pageSize", value = " 单页容量", required = false)
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @ApiParam(name = "pageNo", value = "要求第几页", required = false)
            @RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Map<String,String>filterData=new HashMap<>();
            Map<String,String>sortData=new HashMap<>();

            Map<String,Object>actList=activityService.getList(filterData,sortData,pageSize,pageNo);

            resultMap.put("list",actList);


            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("输入userId,返回user的活动")
    @GetMapping("/getUserList")
    public Result<Map<String,Object>> getUserList(
            @ApiParam(name = "userId", value = " user ID", required = false)
            @RequestParam(name = "userId",required = true) Long userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Map<String,String>filterData=new HashMap<>();
            Map<String,String>sortData=new HashMap<>();

            resultMap=activityService.getUserList(userId);



            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("提交活动海报")
    @PostMapping("/postPoster")
    public Result<Map<String,Object>>postPoster(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
//        System.out.println(multipartFile);

        try
        {
            String url = FileUtil.uploadFile("/activity/poster/"+activityId.toString()+"/", multipartFile);
            Map<String,Object> map1=activityService.updatePoster(url,activityId);
            resultMap.put("url",url);
            resultMap.put("update",map1);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("新建活动")
    @PostMapping("/postActivity")
    public Result<Map<String,Object>> postActivity(
            @ApiParam(name = "activity", value = "activity entity", required = true)
            @RequestBody(required = true) Activity activity
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.addActivity(activity);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("删除一个活动")
    @DeleteMapping("/deleteOne")
    public Result<Map<String,Object>>deleteOne(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam(name ="activityId",required = true) Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.deleteOne(activityId);
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
