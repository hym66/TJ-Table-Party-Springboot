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

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "announce")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Announce {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    Long announceId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    String announceContent;
    @JsonSerialize(using= ToStringSerializer.class)
    Long announceUserId;
    String announcePubTime;
}
