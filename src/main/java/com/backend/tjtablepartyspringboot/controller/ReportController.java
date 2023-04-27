package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.config.TencentCosConfig;
import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.service.ReportService;
import com.backend.tjtablepartyspringboot.config.TencentCosProperties4Picture;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"Report"})
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

//    @Resource
//    private TencentCosProperties4Picture tencentCosProperties4Picture;
//
//    @Resource
//    private TencentCosConfig tencentCosConfig;
//
//    @Resource
//    @Qualifier(TencentCosConfig.COS_IMAGE)
//    private COSClient cosClient4Picture;

    @ApiOperation("新增一个举报单")
    @PostMapping("addReport")
    public Result<Map> addReport(@RequestBody ReportDto reportDto) {
        int res = reportService.addReport(reportDto);
        Long reportId = reportDto.getReportId();

        Map<String, Long> hashMap = new HashMap();
        hashMap.put("reportId", reportId);
        return Result.success(hashMap);
    }


    @ApiOperation("新增一个举报单图片")
    @PostMapping("addReportPhoto")
    public Result<String> addReportPhoto(@RequestParam("file") MultipartFile multipartFile, @RequestParam("reportId") Long reportId){
        //更新数据库中存储的图片url
        Report report = reportService.selectReportByReportId(reportId);

        String url = FileUtil.uploadFile("/report/"+reportId.toString()+"/", multipartFile);

        String oldUrl = report.getPhotoUrl();
        String newUrl = oldUrl == null ? url : oldUrl + url;
        report.setPhotoUrl(oldUrl + newUrl + ";");
        reportService.updateReport(report);

        return Result.success(url);

    }

    @ApiOperation("根据举报单id，获取举报单")
    @GetMapping("getReportById")
    public Result<ReportDto> getReportById(@RequestParam("reportId") Long reportId)
    {
        ReportDto reportDto = reportService.selectReportDtoByReportId(reportId);
        return Result.success(reportDto);
    }

    @ApiOperation("获取所有未审核的举报单")
    @GetMapping("getUncheckedReports")
    public Result<List<ReportDto>> getUncheckedReports()
    {
        List<ReportDto> reportDtoList = reportService.selectUnchecked();
        return Result.success(reportDtoList);
    }
}
