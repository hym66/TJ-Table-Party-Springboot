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
@TableName(value = "club_trpg")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubTrpg {
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    @JsonSerialize(using=ToStringSerializer.class)
    Long trpgId;
    String title;
    String poster;
}
