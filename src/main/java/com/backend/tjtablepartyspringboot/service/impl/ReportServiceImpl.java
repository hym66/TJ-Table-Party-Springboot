package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.backend.tjtablepartyspringboot.mapper.ReportMapper;
import com.backend.tjtablepartyspringboot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportMapper reportMapper;

    @Override
    public int addReport(ReportDto reportDto){
        Report report = new Report(reportDto);
        int res = reportMapper.insert(report);
        return res;
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


}
