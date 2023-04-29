package com.backend.tjtablepartyspringboot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/29
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteTimeDto {
    String week;
    String startTime;
    String endTime;
    boolean open;
}
