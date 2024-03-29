package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/5/31
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateSiteDto {
    Long privateSiteId;
    String creatorId;
    String name;
    String location;
    String picture;
    float latitude;
    float longitude;
    String locationTitle;
    int gameNum;
    List<TrpgPublic> games;

}
