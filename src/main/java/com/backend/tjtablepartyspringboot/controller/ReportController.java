package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.config.TencentCosConfig;
import com.backend.tjtablepartyspringboot.dto.ClubSimpleDto;
import com.backend.tjtablepartyspringboot.dto.ClubUserDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.service.*;
import com.backend.tjtablepartyspringboot.config.TencentCosProperties4Picture;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import com.backend.tjtablepartyspringboot.util.MessageUtil;
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
    @Autowired
    MessageService messageService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ClubService clubService;
    @Autowired
    SiteService siteService;


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
            Report report = reportService.selectReportByReportId(reportId);
            if(agree){
                //给举报者发消息
                Message message = new Message(null, "举报成功反馈", "您的举报经我们核实，被举报者违规行为属实，" +
                        "予以惩罚："+punishment, new Date(), 2);
                messageService.sendMessage(report.getReporterId(), message);

                String targetType = report.getTargetType();
                String criminalId = report.getCriminalId();

                //给被举报者发消息
                if(targetType.equals("user")){
                    Message message1 = new Message(null, "被举报提醒", "您已被举报，予以惩罚："+report.getPunishment()+"。请规范自己的言行！" +
                            "打造绿色网络空间，人人有责！", new Date(), 2);
                    messageService.sendMessage(criminalId, message1);
                }
                else if(targetType.equals("activity")){
                    Activity activity = activityService.getEntityByActivityId(Long.valueOf(criminalId));
                    String activityName = activity.getTitle();

                    Message message2 = new Message(null, "活动违规提醒", "您参与的活动“"+activityName+"”涉嫌违规，已被举报。" +
                            "按照平台规定，该活动已被删除！打造绿色网络空间，人人有责！", new Date(), 0);

                    //遍历活动所有参与者，发消息提醒
                    List<UserJoinActivity> userJoinActivityList = activityService.getUserJoinActivityList(Long.valueOf(criminalId));
                    for(UserJoinActivity uja : userJoinActivityList){
                        String userId = uja.getUserId();
                        messageService.sendMessage(userId, message2);
                    }
                }
                else if(targetType.equals("club")){
                    ClubSimpleDto clubSimpleDto = clubService.getClubSimpleDto(Long.valueOf(criminalId));
                    String clubName = clubSimpleDto.getClubTitle();

                    Message message3 = new Message(null, "俱乐部违规提醒", "您加入的俱乐部“"+clubName+"”涉嫌违规，已被举报。" +
                            "按照平台规定，该俱乐部已被删除！打造绿色网络空间，人人有责！", new Date(), 1);

                    //遍历所有俱乐部参与者，发消息提醒
                    List<ClubUser> clubUserList = clubService.getClubUsers(Long.valueOf(criminalId));
                    for(ClubUser cu : clubUserList){
                        String userId = cu.getUserId();
                        messageService.sendMessage(userId, message3);
                    }
                }
                else if(targetType.equals("site")){
                    PublicSiteDto publicSiteDto = siteService.selectPublicSiteById(Long.valueOf(criminalId));
                    String siteName = publicSiteDto.getName();

                    String creatorId = publicSiteDto.getCreatorId();
                    Message message4 = new Message(null, "场地违规提醒", "您创建的场地“"+siteName+"”涉嫌违规，已被举报。" +
                            "按照平台规定，该场地已被取消入驻！打造绿色网络空间，人人有责！", new Date(), 2);
                    messageService.sendMessage(creatorId, message4);
                }
            }
            else{
                //给被举报者发消息
                Message message = new Message(null, "举报失败反馈", "您的举报经我们核实，不存在违规行为，不予通过。" +
                        "如有疑问，请重新发起举报。",
                        new Date(), 2);
                messageService.sendMessage(report.getReporterId(), message);
            }

            //这里面包括了删除实体的代码
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
