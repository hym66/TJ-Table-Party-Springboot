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
import java.util.*;


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
            @RequestParam("activityId") Long activityId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.getDetail(activityId,userId);
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
            @RequestParam(name ="userId") String userId

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

    @ApiOperation("所有interest这个活动的user的信息列表")
    @GetMapping("/getUserInterestorList")
    public Result<Map<String,Object>>getUserInterestorList(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {

            List<Map<String,Object>>list =activityService.getUserInterestorList(activityId);


            resultMap.put("list",list);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


    @ApiOperation("输入 筛选参数、排序参数，分页返回list")
    @GetMapping("/getList")
    public Result<Map<String,Object>> getList(
            @ApiParam(name = "key", value = "关键词搜索", required = false)
            @RequestParam(name = "key",required = false,defaultValue = "") String key,

            @ApiParam(name = "startDate", value = "开始时间", required = false)
            @RequestParam(name = "startDate",required = false,defaultValue = "") String startDate,
            @ApiParam(name = "endDate", value = "结束时间", required = false)
            @RequestParam(name = "endDate",required = false,defaultValue = "") String endDate,

            @ApiParam(name = "cityCode", value = "城市id", required = true)
            @RequestParam(name = "cityCode",required = false,defaultValue = "") String cityCode,



            @ApiParam(name = "pageSize", value = " 单页容量", required = false)
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @ApiParam(name = "pageNo", value = "要求第几页", required = false)
            @RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Map<String,String>filterData=new HashMap<>();
            filterData.put("startDate",startDate);
            filterData.put("endDate",endDate);
            filterData.put("cityCode",cityCode);
            Map<String,String>sortData=new HashMap<>();

            Map<String,Object>actList=activityService.getList(key,filterData,sortData,pageSize,pageNo);

            resultMap.put("list",actList);


            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }







    @ApiOperation("输入userId,返回user 关注的活动")
    @GetMapping("/getUserInterestList")
    public Result<Map<String,Object>>getUserInterestList(
            @ApiParam(name = "userId", value = " user ID", required = false)
            @RequestParam(name = "userId",required = true) String userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Map<String,String>filterData=new HashMap<>();
            Map<String,String>sortData=new HashMap<>();

            resultMap=activityService.getUserInterestList(userId);



            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("输入userId,返回user的所有活动")
    @GetMapping("/getUserList")
    public Result<Map<String,Object>>getUserList(
            @ApiParam(name = "userId", value = " user ID", required = false)
            @RequestParam(name = "userId",required = true) String userId
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
            @RequestBody(required = true) Activity activity,
            @ApiParam(name = "wishGame", value = "wishGame", required = true)
            @RequestParam(name = "wishGame",required = false) String wishGame
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.addActivity(activity,wishGame);
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

    @ApiOperation("删除一个user参与活动")
    @DeleteMapping("/deleteUserJoin")
    public Result<Map<String,Object>>deleteUserJoin(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam(name ="activityId",required = true) Long activityId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name ="userId",required = true) String userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i= activityService.deleteUserJoin(activityId,userId);
            resultMap.put("i",i);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("输入activity id，获取活动参与者")
    @GetMapping("/getParticipator")
    public Result<Map<String,Object>>getParticipator(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.getActivityParticipator(activityId);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


    @ApiOperation("喜欢或者取消喜欢activity")
    @PostMapping("/doInterest")
    public Result<Map<String,Object>>doInterest(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId,
            @ApiParam(name = "userId", value = "user id", required = true)
            @RequestParam("userId") String userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i=activityService.interest(userId,activityId);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("参与 或者 取消参与activity")
    @PostMapping("/doJoin")
    public Result<Map<String,Object>>doJoin(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId,
            @ApiParam(name = "userId", value = "user id", required = true)
            @RequestParam("userId") String userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i=activityService.doJoin(userId,activityId);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }



    @ApiOperation("修改活动")
    @PostMapping("/modify")
    public Result<Map<String,Object>> modify(
            @ApiParam(name = "activity", value = "activity entity", required = true)
            @RequestBody(required = true) Activity activity,
            @ApiParam(name = "wishGame", value = "wishGame", required = true)
            @RequestParam(name = "wishGame",required = false) String wishGame
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=activityService.modify(activity,wishGame);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("修改活动的state")
    @PostMapping("/modifyState")
    public Result<Map<String,Object>>modifyState(
            @ApiParam(name = "activityId", value = "活动id", required = true)
            @RequestParam("activityId") Long activityId,
            @ApiParam(name = "state", value = "state", required = true)
            @RequestParam("state") String state
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i=activityService.modifyState(activityId,state);
            resultMap.put("i",i);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


    @ApiOperation("输入场地id场地类型，获取相关活动")
    @GetMapping("/getActBySite")
    public Result<List<Activity>>getActBySite(
            @ApiParam(name = "siteId", value = "场地id", required = true)
            @RequestParam("siteId") Long siteId,
            @ApiParam(name = "siteType", value = "场地类型", required = true)
            @RequestParam("siteType") int siteType
    ){
        List<Activity>resultList=new ArrayList<>();
        try
        {
            resultList=activityService.getActBySite(siteId,siteType);
            return Result.success(resultList);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


    @ApiOperation("后端部署上没")
    @GetMapping("/test")
    public Result<Map<String,Object>>test(

    ){
        Map<String,Object>map=new HashMap<>();
        try
        {
            Calendar c=Calendar.getInstance();
            map.put("timeZone",c.getTimeZone());
            return Result.success(map);
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
