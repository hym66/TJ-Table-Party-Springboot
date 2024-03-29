package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.Report;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
    Long addReport(ReportDto reportDto);
    ReportDto selectReportDtoByReportId(Long reportId);
    Report selectReportByReportId(Long reportId);
    int updateReport(Report report);
    List<ReportDto> selectUnchecked();
    int checkReport(Long reportId, Boolean agree, String adminId, String punishment);
}
