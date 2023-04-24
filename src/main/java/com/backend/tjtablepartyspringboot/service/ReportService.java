package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.Report;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    int addReport(Report report);
    Report selectReportByReportId(Long reportId);
    int updateReport(Report report);
}
