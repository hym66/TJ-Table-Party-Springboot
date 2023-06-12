package com.backend.tjtablepartyspringboot.entity;

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
import java.util.List;

/**
 * @Author: 黄彦铭
 * @Date: 2023/05/17/11:00 PM
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "trpg_public_waiting")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrpgPublicWaiting implements Comparable {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private String trpgId;
    private String poster;
    private String titleName;
    private String synopsis;
    private String pictures;
    private String designers;
    private String publishers;
    private String publishLanguages;
    private String publishYear;
    private String publishState;
    private String genre;
    private String gameMode;
    private String portability;
    private String desktopRequirement;
    private String suitableAge;
    private String supportNum;
    private String recommendNum;
    private String averageDuration;
    private String difficulty;
    private String setDuration;
    private String languageRequirement;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date obtainTime;

    @Override
    public int compareTo(Object o) {
        TrpgPublicWaiting t = (TrpgPublicWaiting) o;
        if(obtainTime.before(t.getObtainTime())){
            return -1;
        }
        else if(obtainTime.after(t.getObtainTime())){
            return 1;
        }
        else {
            return 0;
        }
    }
}
