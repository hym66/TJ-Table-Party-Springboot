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
@TableName(value = "club_record")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubRecord {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    Long recordId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    String content;
}