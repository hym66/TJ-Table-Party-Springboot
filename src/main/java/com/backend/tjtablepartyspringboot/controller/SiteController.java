package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.SiteType;
import com.backend.tjtablepartyspringboot.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */

@Api(tags = {"Site"})
@RestController
@RequestMapping("site")
public class SiteController {
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

}
