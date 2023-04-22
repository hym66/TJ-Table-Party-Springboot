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

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "activity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    Long activityId;
    String title;
    int fee;
    int maxPeople;
    int minPeople;
    Date startTime;
    Date endTime;
    Date createTime;
    String summary;
    String description;
    String poster;
    @JsonSerialize(using= ToStringSerializer.class)
    Long siteId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    String state;
}
