package com.backend.tjtablepartyspringboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.PublicSiteMapper;
import com.backend.tjtablepartyspringboot.mapper.SiteTypeMapper;
import com.backend.tjtablepartyspringboot.service.MessageService;
import com.backend.tjtablepartyspringboot.service.SiteService;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import com.backend.tjtablepartyspringboot.util.GeoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */

@Api(tags = {"Site"})
@RestController
@RequestMapping("site")
public class SiteController {

    public static int weekdayUnTrans(String weekday) {
        if (Objects.equals(weekday, "周一")) return 1;
        else if (Objects.equals(weekday, "周二")) return 2;
        else if (Objects.equals(weekday, "周三")) return 3;
        else if (Objects.equals(weekday, "周四")) return 4;
        else if (Objects.equals(weekday, "周五")) return 5;
        else if (Objects.equals(weekday, "周六")) return 6;
        else return 7;
    }

    @Autowired
    private SiteService siteService;

    @Autowired
    private SiteTypeMapper siteTypeMapper;

    @Autowired
    private MessageService messageService;


    @ApiOperation("获取所有公共场地信息")
    @GetMapping("getPublicSiteList")
    public Result<List<PublicSiteBriefDto>> getPublicSiteList() {
        return Result.success(siteService.selectAllPublicSite());
    }

    @ApiOperation("根据ID获取公共场地详细信息")
    @GetMapping("getPublicSiteById")
    public Result<PublicSiteDto> getPublicSiteById(@ApiParam(name = "publicSiteId", value = "公共场地id", required = true)
                                                   @RequestParam("publicSiteId") Long publicSiteId) {
        return Result.success(siteService.selectPublicSiteById(publicSiteId));
    }

    @ApiOperation("根据用户ID获取公共场地信息")
    @GetMapping("getPublicSiteByCreatorId")
    public Result<List<PublicSiteBriefDto>> getPublicSiteByCreatorId(@ApiParam(name = "creatorId", value = "创建者id", required = true)
                                                                     @RequestParam("creatorId") String creatorId) {
        return Result.success(siteService.selectPublicSiteByCreatorId(creatorId));
    }

    @ApiOperation("根据用户ID获取私人场地信息")
    @GetMapping("getPrivateSiteByCreatorId")
    public Result<List<PrivateSite>> getPrivateSiteByCreatorId(@ApiParam(name = "creatorId", value = "创建者id", required = true)
                                                               @RequestParam("creatorId") String creatorId) {
        return Result.success(siteService.selectPrivateSiteByCreatorId(creatorId));
    }

    @ApiOperation("根据ID获取私人场地详细信息")
    @GetMapping("getPrivateSiteById")
    public Result<PrivateSiteDto> getPrivateSiteById(@ApiParam(name = "privateSiteId", value = "私人场地id", required = true)
                                                     @RequestParam("privateSiteId") Long privateSiteId) {
        return Result.success(siteService.selectPrivateSiteById(privateSiteId));
    }


    @ApiOperation("获取所有场地的类型信息")
    @GetMapping("getSiteTypeList")
    public Result<List<SiteType>> getSiteTypeList() {
        return Result.success(siteService.selectAllSiteType());
    }

    @ApiOperation("获取所有场地的标签信息")
    @GetMapping("getSiteTagList")
    public Result<List<SiteTag>> getSiteTagList() {
        return Result.success(siteService.selectAllSiteTag());
    }

    @ApiOperation("创建公共场地")
    @PostMapping("createPublicSite")
    public Result<String> createPublicSite(@RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam("creatorId") String creatorId,
                                           @RequestParam("name") String name,
                                           @RequestParam("type") String type,
                                           @RequestParam("introduction") String introduction,
                                           @RequestParam("city") String city,
                                           @RequestParam("location") String location,
                                           @RequestParam("avgCost") float avgCost,
                                           @RequestParam("capacity") int capacity,
                                           @RequestParam("phone") String phone,
                                           @RequestParam("tag") String tag,
                                           @RequestParam("openTime") String openTime,
                                           @RequestParam("latitude") float latitude,
                                           @RequestParam("longitude") float longitude,
                                           @RequestParam("locationTitle") String locationTitle,
                                           @RequestParam("siteTrpgList") String siteTrpgList
    ) throws ParseException {
        List<SiteTimeDto> openTime_new = new ArrayList<SiteTimeDto>(JSONArray.parseArray(openTime, SiteTimeDto.class));
        List<String> siteTrpgList_new = new ArrayList<>(JSONArray.parseArray(siteTrpgList, String.class));

        // 图片云存储 返回url
        String picture = FileUtil.uploadFile("/report/" + creatorId.toString() + "/", multipartFile);
        // 创建新的公共场地
        PublicSite publicSite = new PublicSite(creatorId, name, city, location, picture, introduction, avgCost, capacity, siteTrpgList_new.size(), phone, new Date(), 2, type, tag, latitude, longitude, locationTitle);
        // 插入数据库
        int res = siteService.insertPublicSite(publicSite);
        if (res == 0) return Result.fail(400, "插入公共场地失败");
        // 获取插入后自增的ID
        Long publicSiteId = publicSite.getPublicSiteId();

        // 插入公共场地的时间信息
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        for (SiteTimeDto op : openTime_new) {
            Time startTime = new Time(sdf.parse(op.getStartTime()).getTime());
            Time endTime = new Time(sdf.parse(op.getEndTime()).getTime());
            int weekday = weekdayUnTrans(op.getWeek());
            PublicSiteTime publicSiteTime = new PublicSiteTime(publicSiteId, weekday, startTime, endTime, op.isOpen());
            int res_ = siteService.insertPublicSiteTime(publicSiteTime);
            if (res_ == 0) return Result.fail(400, "插入公共场地失败");
        }

        // 插入公共场地的游戏信息
        for (String trpgId : siteTrpgList_new) {
            int res_ = siteService.addSiteTrpg(publicSiteId, trpgId, 0);
            if (res_ == 0) return Result.fail(400, "插入公共场地失败");
        }

        return Result.success("插入公共场地成功");
    }

    @ApiOperation("修改公共场地的基本信息，场地图片不修改")
    @PutMapping("modifyPublicSiteWithoutPicture")
    public Result<String> modifyPublicSite(@RequestBody PublicSiteModifyDto pDto,
                                           @RequestParam("siteTrpgList") String siteTrpgList) throws ParseException {

        List<SiteTimeDto> openTime_new = new ArrayList<SiteTimeDto>(JSONArray.parseArray(pDto.getOpenTime(), SiteTimeDto.class));
        List<String> siteTrpgList_new = List.of(siteTrpgList.split(","));

        PublicSite publicSite = new PublicSite(pDto.getPublicSiteId(), pDto.getCreatorId(), pDto.getName(), pDto.getCity(), pDto.getLocation(), pDto.getPicture(), pDto.getIntroduction(), pDto.getAvgCost(), pDto.getCapacity(), siteTrpgList_new.size(), pDto.getPhone(), new Date(), 2, pDto.getType(), pDto.getTag(), pDto.getLatitude(), pDto.getLongitude(), pDto.getLocationTitle());
        int res = siteService.modifyPublicSite(publicSite);
        if (res == 0) return Result.fail(400, "修改公共场地信息失败");

        // 更新公共场地的时间信息
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        for (SiteTimeDto op : openTime_new) {
            Time startTime = new Time(sdf.parse(op.getStartTime()).getTime());
            Time endTime = new Time(sdf.parse(op.getEndTime()).getTime());
            int weekday = weekdayUnTrans(op.getWeek());
            PublicSiteTime publicSiteTime = new PublicSiteTime(pDto.getPublicSiteId(), weekday, startTime, endTime, op.isOpen());
            int res_ = siteService.modifyPublicSiteTime(publicSiteTime);
            if (res_ == 0) return Result.fail(400, "修改公共场地失败");
        }

        // 修改场地的游戏信息
        siteService.deleteSiteTrpg(publicSite.getPublicSiteId(), 0);
        // 插入公共场地的游戏信息
        for (String trpgId : siteTrpgList_new) {
            int res_ = siteService.addSiteTrpg(publicSite.getPublicSiteId(), trpgId, 0);
            if (res_ == 0) return Result.fail(400, "修改公共场地失败");
        }
        return Result.success("修改公共场地成功");
    }

    @ApiOperation("修改公共场地所有信息")
    @PostMapping("modifyPublicSite")
    public Result<String> modifyPublicSite(@RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam("publicSiteId") Long publicSiteId,
                                           @RequestParam("creatorId") String creatorId,
                                           @RequestParam("name") String name,
                                           @RequestParam("type") String type,
                                           @RequestParam("introduction") String introduction,
                                           @RequestParam("city") String city,
                                           @RequestParam("location") String location,
                                           @RequestParam("avgCost") float avgCost,
                                           @RequestParam("capacity") int capacity,
                                           @RequestParam("phone") String phone,
                                           @RequestParam("tag") String tag,
                                           @RequestParam("openTime") String openTime,
                                           @RequestParam("latitude") float latitude,
                                           @RequestParam("longitude") float longitude,
                                           @RequestParam("locationTitle") String locationTitle,
                                           @RequestParam("siteTrpgList") String siteTrpgList
    ) throws ParseException {
        List<SiteTimeDto> openTime_new = new ArrayList<SiteTimeDto>(JSONArray.parseArray(openTime, SiteTimeDto.class));
        List<String> siteTrpgList_new = new ArrayList<>(JSONArray.parseArray(siteTrpgList, String.class));

        // 图片云存储 返回url
        String picture = FileUtil.uploadFile("/report/" + creatorId.toString() + "/", multipartFile);
        // 创建新的公共场地
        PublicSite publicSite = new PublicSite(publicSiteId, creatorId, name, city, location, picture, introduction, avgCost, capacity, siteTrpgList_new.size(), phone, new Date(), 2, type, tag, latitude, longitude, locationTitle);
        // 插入数据库
        int res = siteService.modifyPublicSite(publicSite);
        if (res == 0) return Result.fail(400, "插入公共场地失败");

        // 更新公共场地的时间信息
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        for (SiteTimeDto op : openTime_new) {
            Time startTime = new Time(sdf.parse(op.getStartTime()).getTime());
            Time endTime = new Time(sdf.parse(op.getEndTime()).getTime());
            int weekday = weekdayUnTrans(op.getWeek());
            PublicSiteTime publicSiteTime = new PublicSiteTime(publicSiteId, weekday, startTime, endTime, op.isOpen());
            int res_ = siteService.modifyPublicSiteTime(publicSiteTime);
            if (res_ == 0) return Result.fail(400, "修改公共场地失败");
        }

        siteService.deleteSiteTrpg(publicSite.getPublicSiteId(), 0);
        // 插入公共场地的游戏信息
        for (String trpgId : siteTrpgList_new) {
            int res_ = siteService.addSiteTrpg(publicSite.getPublicSiteId(), trpgId, 0);
            if (res_ == 0) return Result.fail(400, "修改公共场地失败");
        }

        return Result.success("修改公共场地成功");
    }

    @ApiOperation("创建私人场地")
    @PostMapping("createPrivateSite")
    public Result<String> createPrivateSite(@RequestParam("file") MultipartFile multipartFile,
                                            @RequestParam("creatorId") String creatorId,
                                            @RequestParam("name") String name,
                                            @RequestParam("city") String city,
                                            @RequestParam("location") String location,
                                            @RequestParam("latitude") float latitude,
                                            @RequestParam("longitude") float longitude,
                                            @RequestParam("locationTitle") String locationTitle,
                                            @RequestParam("siteTrpgList") String siteTrpgList
    ) {
        List<String> siteTrpgList_new = new ArrayList<>(JSONArray.parseArray(siteTrpgList, String.class));
        int a = siteTrpgList_new.size();
        // 图片云存储 返回url
        String picture = FileUtil.uploadFile("/report/" + creatorId + "/", multipartFile);
        PrivateSite privateSite = new PrivateSite(creatorId, name, city, location, picture, latitude, longitude, locationTitle, siteTrpgList_new.size());
        int res = siteService.insertPrivateSite(privateSite);
        if (res == 0) return Result.fail(400, "创建私人场地失败");

        // 获取自增的ID
        Long privateSiteId = privateSite.getPrivateSiteId();

        // 插入私人场地的游戏信息
        for (String trpgId : siteTrpgList_new) {
            int res_ = siteService.addSiteTrpg(privateSiteId, trpgId, 1);
            if (res_ == 0) return Result.fail(400, "创建私人场地失败");
        }
        return Result.success("创建私人场地成功");
    }

    @ApiOperation("修改私人场地的基本信息，场地图片不修改")
    @PutMapping("modifyPrivateSiteWithoutPicture")
    public Result<String> modifyPrivateSiteWithoutPicture(@RequestBody PrivateSite privateSite,
                                                          @RequestParam("siteTrpgList") String siteTrpgList) {

        int res = siteService.modifyPrivateSite(privateSite);
        if (res == 0) return Result.fail(400, "修改私人场地信息失败");
        // 修改场地的游戏信息
        List<String> siteTrpgList_new = List.of(siteTrpgList.split(","));
        siteService.deleteSiteTrpg(privateSite.getPrivateSiteId(), 1);
        // 插入私人场地的游戏信息
        for (String trpgId : siteTrpgList_new) {
            int res_ = siteService.addSiteTrpg(privateSite.getPrivateSiteId(), trpgId, 1);
            if (res_ == 0) return Result.fail(400, "插入私人场地失败");
        }
        return Result.success("修改私人场地信息成功");
    }

    @ApiOperation("修改私人场地的所有信息")
    @PostMapping("modifyPrivateSite")
    public Result<String> modifyPrivateSite(@RequestParam("file") MultipartFile multipartFile,
                                            @RequestParam("privateSiteId") Long privateSiteId,
                                            @RequestParam("creatorId") String creatorId,
                                            @RequestParam("name") String name,
                                            @RequestParam("city") String city,
                                            @RequestParam("location") String location,
                                            @RequestParam("latitude") float latitude,
                                            @RequestParam("longitude") float longitude,
                                            @RequestParam("locationTitle") String locationTitle,
                                            @RequestParam("siteTrpgList") String siteTrpgList
    ) {
        // 图片云存储 返回url
        String picture = FileUtil.uploadFile("/report/" + creatorId + "/", multipartFile);
        List<String> siteTrpgList_new = new ArrayList<>(JSONArray.parseArray(siteTrpgList, String.class));
        PrivateSite privateSite = new PrivateSite(privateSiteId, creatorId, name, city, location, picture, latitude, longitude, locationTitle, siteTrpgList_new.size());
        int res = siteService.modifyPrivateSite(privateSite);
        // 删除原先的游戏信息
        siteService.deleteSiteTrpg(privateSite.getPrivateSiteId(), 1);
        // 插入私人场地的游戏信息
        for (String trpgId : siteTrpgList_new) {
            int res_ = siteService.addSiteTrpg(privateSite.getPrivateSiteId(), trpgId, 1);
            if (res_ == 0) return Result.fail(400, "插入私人场地失败");
        }
        return Result.success("修改私人场地信息成功");
    }

    @ApiOperation("删除私人场地")
    @DeleteMapping("deletePrivateSite")
    public Result<String> deletePrivateSite(@ApiParam(name = "privateSiteId", value = "私人场地Id", required = true)
                                            @RequestParam("privateSiteId") Long privateSiteId) {
        int res = siteService.deletePrivateSite(privateSiteId);
        if (res == 0) return Result.fail(400, "删除私人场地失败");
        else return Result.success("删除私人场地成功");
    }

    @ApiOperation("场地条件筛选")
    @PostMapping("getConditionSites")
    public Result<List<PublicSiteBriefDto>> getConditionSites(@RequestBody SiteConditionDto siteConditionDto) {
        Integer maxCapacity = siteConditionDto.getMaxCapacity();
        Integer minCapacity = siteConditionDto.getMinCapacity();
        String city = siteConditionDto.getCity();
        String keyword = siteConditionDto.getKeyword();
        Float latitude = siteConditionDto.getLatitude();
        Float longitude = siteConditionDto.getLongitude();
        Float maxDistance = siteConditionDto.getMaxDistance();
        Float minDistance = siteConditionDto.getMinDistance();

        //1.关键词筛选
        List<PublicSite> publicSiteList = siteService.selectByKeyword(keyword);


        //2.容量筛选
        if (maxCapacity != -1 && minCapacity != -1) {
            publicSiteList = publicSiteList.stream()
                    .filter((PublicSite c) -> ((c.getCapacity() <= maxCapacity) && (c.getCapacity() >= minCapacity)))
                    .collect(Collectors.toList());
        }


        //3.距离筛选
        if (maxDistance != -1 && minDistance != -1) {
            publicSiteList = publicSiteList.stream()
                    .filter((PublicSite c) -> {
                        float dis = GeoUtil.getDistance(longitude, latitude, c.getLongitude(), c.getLatitude());
                        return dis >= minDistance && dis <= maxDistance;
                    })
                    .collect(Collectors.toList());
        }
        ArrayList<PublicSiteBriefDto> res = new ArrayList<>();
        for (PublicSite ps : publicSiteList) {
            String[] type = ps.getType().split(",");
            for (int i = 0; i < type.length; i++) {
                type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
            }
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getStatus());
            res.add(publicSiteBriefDto);
        }
        return Result.success(res);
    }

    @ApiOperation("场地信息反馈")
    @PostMapping("siteInfoFeedback")
    public Result<String> siteInfoFeedback(@RequestBody HashMap<String, String> map) {
        String creatorId = map.get("creatorId");
        Long publicSiteId = Long.valueOf(map.get("publicSiteId"));
        String name = map.get("name");
        String content = map.get("content");
        Date time = new Date();

        Message message = new Message(publicSiteId, name + " 信息反馈", content, time, 2);
        int res = messageService.sendMessage(creatorId, message);
        if (res == 0) return Result.fail(400, "公共场地信息反馈失败");
        else return Result.success("公共场地信息反馈成功");
    }

    @ApiOperation("获取该活动对应的所有question信息列表")
    @GetMapping("/getQuestionList")
    public Result<List<Map<String, Object>>> getQuestionList(
            @ApiParam(name = "publicSiteId", value = "场地id", required = true)
            @RequestParam("publicSiteId") Long publicSiteId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name = "userId") String userId

    ) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {

            list = siteService.getQuestionList(publicSiteId, userId);

            return Result.success(list);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }

    }

    @ApiOperation("获取该question对应的reply信息列表")
    @GetMapping("/getReplyList")
    public Result<Map<String, Object>> getReplyList(
            @ApiParam(name = "questionId", value = "问题id", required = true)
            @RequestParam("questionId") Long questionId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name = "userId") String userId
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = siteService.getReplyList(questionId, userId);
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }

    }

    @ApiOperation("question的UserLike")
    @GetMapping("/getUserLikeQuestionList")
    public Result<Map<String, Object>> getUserLikeQuestionList(
            @ApiParam(name = "questionId", value = "问题id", required = true)
            @RequestParam("questionId") Long questionId
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<SiteUserLikeQuestion> userLikeQuestionList = siteService.getUserLikeQuestionList(questionId);
            resultMap.put("data", userLikeQuestionList);
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }

    }

    @ApiOperation("reply的UserLike")
    @GetMapping("/getUserLikeReplyList")
    public Result<Map<String, Object>> getUserLikeReplyList(
            @ApiParam(name = "replyId", value = "回答id", required = true)
            @RequestParam("replyId") Long replyId
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<SiteUserLikeReply> userLikeReplyList = siteService.getUserLikeReplyList(replyId);
            resultMap.put("data", userLikeReplyList);
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }

    }


    @ApiOperation("发布新的reply")
    @PostMapping("/postReply")
    public Result<Map<String, Object>> postReply(
            @ApiParam(name = "replyData", value = "reply字典", required = true)
            @RequestBody(required = true) Map<String, String> replyData

    ) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Long questionId = Long.parseLong(replyData.get("questionId"));
            String userId = replyData.get("userId");
            String content = replyData.get("content");
            String anonymity = replyData.get("anonymity");

            Integer i = siteService.addReply(questionId, userId, content, anonymity);
            resultMap.put("i", i);

            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }


    }


    @ApiOperation("发布新的question")
    @PostMapping("/postQuestion")
    public Result<Map<String, Object>> postQuestion(
            @ApiParam(name = "questionData", value = "question字典", required = true)
            @RequestBody(required = true) Map<String, String> questionData

    ) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            String userId = questionData.get("userId");
            Long publicSiteId = Long.parseLong(questionData.get("publicSiteId"));
            String content = questionData.get("content");
            String title = questionData.get("title");
            String anonymity = questionData.get("anonymity");

            Integer i = siteService.addQuestion(publicSiteId, userId, content, title, anonymity);
            resultMap.put("i", i);

            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }


    }


    @ApiOperation("user 点赞/取消点赞一条reply")
    @PostMapping("/likeReply")
    public Result<Map<String, Object>> likeReply(
            @ApiParam(name = "userLikeReplyMap", value = "userLikeReply字典", required = true)
            @RequestBody(required = true) Map<String, String> userLikeReplyMap
    ) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Long replyId = Long.parseLong(userLikeReplyMap.get("replyId"));
            String userId = userLikeReplyMap.get("userId");

            Integer i = siteService.userLikeOneReply(userId, replyId);
            resultMap.put("i", i);

            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }


    }


    @ApiOperation("user 点赞/取消点赞一条 question")
    @PostMapping("/likeQuestion")
    public Result<Map<String, Object>> likeQuestion(
            @ApiParam(name = "userLikeQuestionMap", value = "userLikeReply字典", required = true)
            @RequestBody(required = true) Map<String, String> userLikeQuestionMap
    ) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Long questionId = Long.parseLong(userLikeQuestionMap.get("questionId"));
            String userId = userLikeQuestionMap.get("userId");

            Integer i = siteService.userLikeOneQuestion(userId, questionId);
            resultMap.put("i", i);

            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }


    }


    @ApiOperation("删除一条reply")
    @DeleteMapping("/deleteReply")
    public Result<Map<String, Object>> deleteReply(
            @ApiParam(name = "replyId", value = "回答id", required = true)
            @RequestParam("replyId") Long replyId
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer i = siteService.deleteReply(replyId);
            resultMap.put("i", i);
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }


    }


    @ApiOperation("删除一条 question")
    @DeleteMapping("/deleteQuestion")
    public Result<Map<String, Object>> deleteQuestion(
            @ApiParam(name = "questionId", value = "问题id", required = true)
            @RequestParam("questionId") Long questionId
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer i = siteService.deleteQuestion(questionId);
            resultMap.put("i", i);
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.fail(0, e.getMessage());
        }


    }
}
