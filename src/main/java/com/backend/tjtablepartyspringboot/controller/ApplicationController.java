package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.AppDto;
import com.backend.tjtablepartyspringboot.dto.AppSimpleDto;
import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.mapper.ApplicationMapper;
import com.backend.tjtablepartyspringboot.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Application"})
@RestController
@RequestMapping("application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;

    @ApiOperation("根据公用场地id，获取场地申请单内容")
    @GetMapping("getAppById")
    public Result<AppDto> getAppById(@ApiParam(name="publicSiteId", value="公用场地id", required = true)
                                         @RequestParam("publicSiteId") Long publicSiteId)
    {
        AppDto appDto = applicationService.selectBySiteId(publicSiteId);
        return Result.success(appDto);
    }

    @ApiOperation("获取所有未审核的场地申请单")
    @GetMapping("getUncheckedReports")
    public Result<List<AppSimpleDto>> getUncheckedApps()
    {
        List<AppSimpleDto> appSimpleDtoList = applicationService.selectUnchecked();
        return Result.success(appSimpleDtoList);
    }
}
