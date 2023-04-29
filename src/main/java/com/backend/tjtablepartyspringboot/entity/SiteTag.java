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
 * @Date 2023/4/23
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "site_tag")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteTag {
    @JsonSerialize(using= ToStringSerializer.class)
    Long siteTagId;
    String siteTagName;
}

