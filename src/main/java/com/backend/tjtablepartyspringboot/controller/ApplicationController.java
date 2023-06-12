package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.AppDto;
import com.backend.tjtablepartyspringboot.dto.AppSimpleDto;
import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.mapper.ApplicationMapper;
import com.backend.tjtablepartyspringboot.mapper.PublicSiteMapper;
import com.backend.tjtablepartyspringboot.service.ApplicationService;
import com.backend.tjtablepartyspringboot.service.SiteService;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"Application"})
@RestController
@RequestMapping("application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    PublicSiteMapper publicSiteMapper;

    @ApiOperation("根据公用场地id，获取场地申请单内容")
    @GetMapping("getAppById")
    public Result<AppDto> getAppById(@ApiParam(name="publicSiteId", value="公用场地id", required = true)
                                         @RequestParam("publicSiteId") Long publicSiteId)
    {
        AppDto appDto = applicationService.selectBySiteId(publicSiteId);
        return Result.success(appDto);
    }

    @ApiOperation("获取所有未审核的场地申请单")
    @GetMapping("getUncheckedApps")
    public Result<List<AppSimpleDto>> getUncheckedApps()
    {
        List<AppSimpleDto> appSimpleDtoList = applicationService.selectUnchecked();
        return Result.success(appSimpleDtoList);
    }

    @ApiOperation("审核申请单")
    @GetMapping("checkApp")
    @Transactional
    public Result<String> checkApp(@ApiParam(name="publicSiteId", value="公用场地id", required = true)
                                       @RequestParam("publicSiteId") Long publicSiteId,
                                   @ApiParam(name="agree", value="是否同意该场地入驻", required = true)
                                        @RequestParam("agree") Boolean agree,
                                   @ApiParam(name="adminId", value="审核的管理员id", required = true)
                                       @RequestParam("adminId") String adminId,
                                   @ApiParam(name="adminMessage", value="管理员审核意见", required = true)
                                       @RequestParam("adminMessage") String adminMessage)
    {
        try {
            int res = applicationService.adminCheck(publicSiteId, agree, adminId, adminMessage);
            return Result.success("审核保存成功！");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(500,"审核失败！检查后端");
        }

    }

    @ApiOperation("新增一个举报单图片")
    @PostMapping("addAppPhoto")
    public Result<String> addAppPhoto(@RequestParam("file") MultipartFile multipartFile, @RequestParam("publicSiteId") Long publicSiteId){
        String lock = "";

        synchronized (lock) {
            //更新数据库中存储的图片url
            PublicSite publicSite = publicSiteMapper.selectPublicSiteById(publicSiteId);

            String url = FileUtil.uploadFile("/application/" + publicSiteId.toString() + "/", multipartFile);

            String oldUrl = publicSite.getAdminPhotos();
            String newUrl = oldUrl == null ? url : oldUrl + url;
            publicSite.setAdminPhotos(newUrl + ";");
            int res = publicSiteMapper.updateById(publicSite);
            return Result.success(url);
        }
    }
}
