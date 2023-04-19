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

/**
 * @Author: 杨严
 * @Date: 2023/04/19/2:22 PM
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "trpg")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trpg {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    

}
