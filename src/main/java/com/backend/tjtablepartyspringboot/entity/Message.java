package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @JsonSerialize(using= ToStringSerializer.class)
    Long messageId;
    @JsonSerialize(using=ToStringSerializer.class)
    Long sourceId;
    String title;
    String content;
    Date time;
    int type;
}
