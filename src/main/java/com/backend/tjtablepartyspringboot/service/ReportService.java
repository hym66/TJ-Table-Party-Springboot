package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.backend.tjtablepartyspringboot.entity.Report;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    int addReport(ReportDto reportDto);
    ReportDto selectReportDtoByReportId(Long reportId);
    Report selectReportByReportId(Long reportId);
    int updateReport(Report report);
}
