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

    /**
     *     如果 type==0，sourceId 即为 activityId
     *     如果 type==1，sourceId 即为 clubId
     */
    @JsonSerialize(using=ToStringSerializer.class)
    Long sourceId;
    String title;
    String content;
    Date time;
    /**
     *  0 活动消息
     *  1 俱乐部消息
     *  2 其他消息
     */
    int type;


    public Message(Long sourceId, String title, String content, Date time, int type) {
        this.sourceId = sourceId;
        this.title = title;
        this.content = content;
        this.time = time;
        this.type = type;
    }
}
