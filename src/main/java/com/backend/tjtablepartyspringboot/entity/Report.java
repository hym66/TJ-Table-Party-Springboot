package com.backend.tjtablepartyspringboot.entity;

import com.backend.tjtablepartyspringboot.dto.ReportDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "report")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    Long reportId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long reporterId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long criminalId;
    String targetType;
    String faultType;
    String photoUrl;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date uploadTime;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date checkTime;
    Byte isPassed;
    String description;

    public Report(ReportDto reportDto){
        if(reportDto == null){
            return;
        }
        this.reportId = reportDto.getReportId();
        this.reporterId = reportDto.getReporterId();
        this.criminalId = reportDto.getCriminalId();
        this.targetType = reportDto.getTargetType();

        StringBuilder stringBuilder = new StringBuilder();
        String[] faultTypeList = reportDto.getFaultTypeList();
        for(String str : faultTypeList){
            stringBuilder.append(str);
        }
        this.faultType = String.valueOf(stringBuilder);

        this.uploadTime = reportDto.getUploadTime();
        this.checkTime = reportDto.getCheckTime();
        this.isPassed = reportDto.getIsPassed();
        this.description = reportDto.getDescription();
    }
}
