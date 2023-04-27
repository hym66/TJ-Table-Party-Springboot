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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using=ToStringSerializer.class)
    private Long userId;
    private String nickName;
    private String avatarUrl;
    private String province;
    private String city;
    private String gender;
}

