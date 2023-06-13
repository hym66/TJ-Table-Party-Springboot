package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

/**
 * @Author: 杨严
 * @Date: 2023/04/19/8:17 PM
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "activity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long activityId;
    private String userId;
    private String title;
    private Integer fee;
    private Integer maxPeople;
    private Integer minPeople;
    private Integer nowPeople;


    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale="zh_CN", timezone="Asia/Shanghai", pattern="yyyy-MM-dd hh:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale="zh_CN", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale="zh_CN", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    @TableField( "create_time")
    private Date createTime;


    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale="zh_CN", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    @TableField( "repeat_time")
    private Date repeatTime;

    private Integer repeatDay;
    private Integer repeatNum;



    private String summary;
    private String description;
    private String poster;
    private String pictures;
    private Long siteId;
    private int siteType;
    private Long clubId;


    /**
     * 活动的状态：0正在召集，1集合完成，2进行中，3已结束，4已删除
     */
    private String state;








}
