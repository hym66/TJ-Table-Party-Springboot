package com.backend.tjtablepartyspringboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.PublicSiteMapper;
import com.backend.tjtablepartyspringboot.mapper.SiteTypeMapper;
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

    @ApiOperation("根据用户ID获取私人场地信息")
    @GetMapping("getPrivateSiteByCreatorId")
    public Result<List<PrivateSite>> getPrivateSiteByCreatorId(@ApiParam(name = "creatorId", value = "创建者id", required = true)
                                                               @RequestParam("creatorId") Long creatorId) {
        return Result.success(siteService.selectPrivateSiteByCreatorId(creatorId));
    }

    @ApiOperation("根据ID获取私人场地详细信息")
    @GetMapping("getPrivateSiteById")
    public Result<PrivateSite> getPrivateSiteById(@ApiParam(name = "privateSiteId", value = "私人场地id", required = true)
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
                                           @RequestParam("longitude") float longitude
    ) throws ParseException {
        List<SiteTimeDto> openTime_new = new ArrayList<SiteTimeDto>(JSONArray.parseArray(openTime, SiteTimeDto.class));

        // 图片云存储 返回url
        String picture = FileUtil.uploadFile("/report/" + creatorId.toString() + "/", multipartFile);
        // 创建新的公共场地
        PublicSite publicSite = new PublicSite(creatorId, name, city, location, picture, introduction, avgCost, capacity, 0, phone, new Date(), 0, type, tag, latitude, longitude);
        // 插入数据库
        int res = siteService.insertPublicSite(publicSite);
        if (res == 0) return Result.fail(400, "插入公共场地失败");
        // 获取插入后自增的ID
        Long publicSiteId = publicSite.getPublicSiteId();


        DateFormat sdf = new SimpleDateFormat("HH:mm");
        for (SiteTimeDto op : openTime_new) {
            Time startTime = new Time(sdf.parse(op.getStartTime()).getTime());
            Time endTime = new Time(sdf.parse(op.getEndTime()).getTime());
            int weekday = weekdayUnTrans(op.getWeek());
            PublicSiteTime publicSiteTime = new PublicSiteTime(publicSiteId, weekday, startTime, endTime, op.isOpen());
            int res_ = siteService.insertPublicSiteTime(publicSiteTime);
            if (res_ == 0) return Result.fail(400, "插入公共场地失败");
        }
        return Result.success("插入公共场地成功");
    }

    @ApiOperation("创建私人场地")
    @PostMapping("createPrivateSite")
    public Result<String> createPrivateSite(@RequestParam("file") MultipartFile multipartFile,
                                            @RequestParam("creatorId") String creatorId,
                                            @RequestParam("name") String name,
                                            @RequestParam("location") String location,
                                            @RequestParam("latitude") float latitude,
                                            @RequestParam("longitude") float longitude) {
        // 图片云存储 返回url
        String picture = FileUtil.uploadFile("/report/" + creatorId.toString() + "/", multipartFile);
        PrivateSite privateSite = new PrivateSite(creatorId, name, location, picture, latitude, longitude);
        int res = siteService.insertPrivateSite(privateSite);
        if (res == 0) return Result.fail(400, "创建私人场地失败");
        else return Result.success("创建私人场地成功");
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
        publicSiteList = publicSiteList.stream()
                .filter((PublicSite c) -> ((c.getCapacity() <= maxCapacity) && (c.getCapacity() >= minCapacity)))
                .collect(Collectors.toList());

        //3.距离筛选
        if (maxDistance != null || minDistance != null) {
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
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum());
            res.add(publicSiteBriefDto);
        }
        return Result.success(res);
    }

}
