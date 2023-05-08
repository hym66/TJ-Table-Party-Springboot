package com.backend.tjtablepartyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//比起ClubUser多了role
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubUserDto {
    Long clubId;
    String userId;
    String role;
    String name;
    String avatar;
}
