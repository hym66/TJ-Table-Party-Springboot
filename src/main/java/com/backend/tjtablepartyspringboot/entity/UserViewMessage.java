package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_view_message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserViewMessage {
    @JsonSerialize(using= ToStringSerializer.class)
    Long userId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long messageId;
    int isView;
}
