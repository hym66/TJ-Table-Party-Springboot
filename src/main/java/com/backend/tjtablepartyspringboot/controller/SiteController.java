package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @ApiOperation("获取所有场地信息")
    @GetMapping("getPublicSiteList")
    public Result<List<PublicSiteBriefDto>> getPublicSiteList() {
        return Result.success(siteService.selectAllPublicSite());
    }

    @ApiOperation("根据ID获取场地信息")
    @GetMapping("getPublicSiteById")
    public Result<PublicSiteDto> getPublicSiteById(@ApiParam(name = "publicSiteId", value = "场地id", required = true)
                                                   @RequestParam("publicSiteId") Long publicSiteId) {
        return Result.success(siteService.selectPublicSiteById(publicSiteId));
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
    public Result<String> createPublicSite(@RequestBody HashMap<String, Object> map) throws ParseException {
        HashMap<String, Object> formData = (HashMap<String, Object>) map.get("formData");
        String creatorId = formData.get("creatorId").toString();
        String name = (String) formData.get("name");
        String type = (String) formData.get("type");
        String introduction = (String) formData.get("introduction");
        String picture = (String) formData.get("picture");
        String city = (String) formData.get("city");
        String location = (String) formData.get("location");
        float avgCost = Float.parseFloat(formData.get("avgCost").toString());
        int capacity = (int) formData.get("capacity");
        String phone = (String) formData.get("phone");
        String tag = (String) formData.get("tag");
        ArrayList<HashMap<String, String>> openTime = (ArrayList<HashMap<String, String>>) formData.get("openTime");
        float latitude = Float.parseFloat(formData.get("latitude").toString());
        float longitude = Float.parseFloat(formData.get("longitude").toString());
        // 创建新的公共场地
        PublicSite publicSite = new PublicSite(creatorId, name, city, location, picture, introduction, avgCost, capacity, 0, phone, new Date(), 0, type, tag, latitude, longitude);
        // 插入数据库
        int res = siteService.insertPublicSite(publicSite);
        if (res == 0) return Result.fail(400, "插入公共场地失败");
        // 获取插入后自增的ID
        Long publicSiteId = publicSite.getPublicSiteId();


        DateFormat sdf = new SimpleDateFormat("HH:mm");
        for (HashMap<String, String> op: openTime) {
            Time startTime = new Time(sdf.parse(op.get("startTime")).getTime());
            Time endTime = new Time(sdf.parse(op.get("endTime")).getTime());
            int weekday = weekdayUnTrans(op.get("week"));
            PublicSiteTime publicSiteTime = new PublicSiteTime(publicSiteId, weekday, startTime, endTime);
            int res_ = siteService.insertPublicSiteTime(publicSiteTime);
            if (res_ == 0) return Result.fail(400, "插入公共场地失败");
        }

        return Result.success("插入公共场地成功");
    }
    @ApiOperation("创建私人场地")
    @PostMapping("createPrivateSite")
    public Result<String> createPrivateSite(@RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> formData = (HashMap<String, Object>) map.get("formData");
        String creatorId = (String) formData.get("creatorId");
        String name = (String) formData.get("name");
        String city = (String) formData.get("city");
        String location = (String) formData.get("location");
        String picture = (String) formData.get("picture");
        String introduction = (String) formData.get("introduction");
        float latitude = Float.parseFloat(formData.get("latitude").toString());
        float longitude = Float.parseFloat(formData.get("longitude").toString());
        PrivateSite privateSite = new PrivateSite(creatorId, name, city, location, picture, introduction, latitude, longitude);
        int res = siteService.insertPrivateSite(privateSite);
        if (res == 0) return Result.fail(400, "创建私人场地失败");
        else return Result.success("创建私人场地成功");
    }
}
