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
 * @Author: 杨严
 * @Date: 2023/04/19/8:22 PM
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_join_activity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserJoinActivity {
    private Long userId;
    private Long activityId;

    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    private Date joinTime;
}
