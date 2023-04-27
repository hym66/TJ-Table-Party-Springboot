package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/8:10 PM
 * @Description:
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "trpg_private")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrpgPrivate {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private String trpgId;
    private Long userId;

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



}
