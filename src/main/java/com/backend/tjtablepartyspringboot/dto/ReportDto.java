package com.backend.tjtablepartyspringboot.dto;

import com.alibaba.fastjson.annotation.JSONType;
import com.backend.tjtablepartyspringboot.entity.Report;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDto implements Comparable {
    @JsonSerialize(using= ToStringSerializer.class)
    Long reportId;
    @JsonSerialize(using= ToStringSerializer.class)
    String reporterId;
    String criminalId;
    String targetType;
    String[] faultTypeList;
    String[] photoList;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date uploadTime;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date checkTime;
    Byte isPassed;
    String description;

    public ReportDto(Report report){
        if(report == null){
            return;
        }
        this.reportId = report.getReportId();
        this.reporterId = report.getReporterId();
        this.criminalId = report.getCriminalId();
        this.targetType = report.getTargetType();
        if(report.getFaultType() != null) {
            String faultType = report.getFaultType();
            String[] tmp = faultType.split(",");

            this.faultTypeList = report.getFaultType().split(",");
        }
        if(report.getPhotoUrl() != null) {
            this.photoList = report.getPhotoUrl().split(";");
        }
        this.uploadTime = report.getUploadTime();
        this.checkTime = report.getCheckTime();
        this.isPassed = report.getIsPassed();
        this.description = report.getDescription();
    }

    @Override
    public int compareTo(Object o) {
        ReportDto r = (ReportDto) o;
        if(uploadTime.before(r.getUploadTime())){
            return -1;
        }
        else if(uploadTime.after(r.getUploadTime())){
            return 1;
        }
        else{
            return 0;
        }
    }
}
