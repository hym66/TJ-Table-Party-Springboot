package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.entity.User;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.accessibility.AccessibleIcon;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ClubMapper clubMapper;
    @Autowired
    PublicSiteMapper publicSiteMapper;


    @Override
    public Long addReport(ReportDto reportDto){
        Report report = new Report(reportDto);
        int res = reportMapper.insert(report);

        Long reportId = report.getReportId();
        return reportId;
    }

    @Override
    public ReportDto selectReportDtoByReportId(Long reportId) {
        Report report = reportMapper.selectByReportId(reportId);
        //组装Dto
        ReportDto dto = new ReportDto(report);
        return dto;
    }

    @Override
    public Report selectReportByReportId(Long reportId){
        Report report = reportMapper.selectByReportId(reportId);
        return report;
    }

    @Override
    public int updateReport(Report report) {
        reportMapper.updateById(report);
        return 0;
    }

    @Override
    public List<ReportDto> selectUnchecked() {
        List<Report> reportList = reportMapper.selectUnchecked();
        List<ReportDto> dtoList = reportList.stream().map(report -> new ReportDto(report)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public int checkReport(Long reportId, Boolean agree, String adminId, String punishment) {
        Report report = reportMapper.selectByReportId(reportId);
        Byte isPassed = agree ? Byte.valueOf("1") : Byte.valueOf("0");
        report.setIsPassed(isPassed);
        report.setCheckTime(new Date());
        report.setAdminId(adminId);
        report.setPunishment(punishment);
        int res = reportMapper.updateById(report);

        //如果举报单审核通过了，要实施制裁
        String criminalId = report.getCriminalId();
        //todo: 给用户发消息提醒
        if(isPassed == 1) {
            String targetType = report.getTargetType();
            if (targetType.equals("user")) {
                int banDays = Integer.valueOf(punishment.split("天")[0].split("封号")[0]);
                //给用户表封号
                Calendar now =Calendar.getInstance();
                now.setTime(new Date());
                now.set(Calendar.DATE,now.get(Calendar.DATE)+banDays);
                Date bantime = now.getTime();

                User user = userMapper.selectById(criminalId);
                user.setBantime(bantime);
                res = userMapper.updateById(user);
            }
            else if(targetType.equals("activity")){
                Long activityId = Long.valueOf(criminalId);
                res = activityMapper.deleteById(activityId);
            }
            else if(targetType.equals("club")){
                Long clubId = Long.valueOf(criminalId);
                res = clubMapper.deleteById(clubId);
            }
            else if(targetType.equals("site")){
                //默认只有公用场地会被举报
                Long publicSiteId = Long.valueOf(criminalId);
                res = publicSiteMapper.deleteById(publicSiteId);
            }
        }

        return res;
    }


}
