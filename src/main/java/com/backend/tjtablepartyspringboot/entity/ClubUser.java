package com.backend.tjtablepartyspringboot.entity;

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
@TableName(value = "club_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubUser {
    @JsonSerialize(using= ToStringSerializer.class)
    String userId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
}
