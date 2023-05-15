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
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        reportDto.setUploadTime(new Date());
        Long reportId = reportService.addReport(reportDto);

        Map<String, Long> hashMap = new HashMap();
        hashMap.put("reportId", reportId);
        return Result.success(hashMap);
    }


    @ApiOperation("新增一个举报单图片")
    @PostMapping("addReportPhoto")
    public Result<String> addReportPhoto(@RequestParam("file") MultipartFile multipartFile, @RequestParam("reportId") Long reportId){
        String lock = "";

        synchronized (lock) {
            //更新数据库中存储的图片url
            Report report = reportService.selectReportByReportId(reportId);

            String url = FileUtil.uploadFile("/report/" + reportId.toString() + "/", multipartFile);

            String oldUrl = report.getPhotoUrl();
            String newUrl = oldUrl == null ? url : oldUrl + url;
            report.setPhotoUrl(newUrl + ";");
            reportService.updateReport(report);
            return Result.success(url);
        }



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

        //按时间升序
        reportDtoList.stream().sorted().collect(Collectors.toList());
        return Result.success(reportDtoList);
    }

    @ApiOperation("审核举报单")
    @GetMapping("checkReport")
    @Transactional
    public Result<String> checkReport(@ApiParam(name="reportId", value="举报单id", required = true)
                                   @RequestParam("reportId") Long reportId,
                                   @ApiParam(name="agree", value="是否同意该场地入驻", required = true)
                                   @RequestParam("agree") Boolean agree,
                                   @ApiParam(name="adminId", value="审核的管理员id", required = true)
                                   @RequestParam("adminId") String adminId,
                                      @ApiParam(name="punishment", value="惩罚内容", required = true)
                                          @RequestParam("punishment") String punishment)

    {
        try {
            int res = reportService.checkReport(reportId, agree, adminId, punishment);
            return Result.success("审核保存成功！");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(500,"审核失败！检查后端");
        }
    }
}
