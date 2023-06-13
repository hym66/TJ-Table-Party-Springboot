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
 * @Author 2051196 刘一飞
 * @Date 2023/6/7
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "public_site_reply")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicSiteReply {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long replyId;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long questionId;
    private String content;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(  timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    @TableField( "create_time")
    private Date createTime;
    private String anonymity;
    private String displayName;
    private String displayAvatar;
    private String userId;
    private Integer likeTotal;
}
