package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.mapper.ReportMapper;
import com.backend.tjtablepartyspringboot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportMapper reportMapper;

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
    public int checkReport(Long reportId, Boolean agree, String adminId) {
        Report report = reportMapper.selectByReportId(reportId);
        Byte isPassed = agree ? Byte.valueOf("1") : Byte.valueOf("0");
        report.setIsPassed(isPassed);
        report.setCheckTime(new Date());
        report.setAdminId(adminId);
        int res = reportMapper.updateById(report);
        return res;
    }


}
