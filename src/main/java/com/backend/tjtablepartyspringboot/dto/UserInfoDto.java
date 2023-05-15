package com.backend.tjtablepartyspringboot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto {
    private String userId;
    private String nickName;
    private String avatarUrl;
    private String province;
    private String city;
    private String gender;
    private String ban;
    // getter and setter methods
}