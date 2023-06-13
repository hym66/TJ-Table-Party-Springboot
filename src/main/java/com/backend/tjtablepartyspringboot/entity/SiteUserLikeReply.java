package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@TableName(value = "site_user_like_reply")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteUserLikeReply {
    private String userId;
    private Long replyId;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale="zh_CN", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    private Date createTime;
}
