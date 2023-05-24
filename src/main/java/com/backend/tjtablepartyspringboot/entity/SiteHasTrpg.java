package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/5/17
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "site_has_trpg")
public class SiteHasTrpg {
    @JsonSerialize(using= ToStringSerializer.class)
    Long siteId;
    String trpgId;
    int siteType;
}
