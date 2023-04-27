package com.backend.tjtablepartyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubUserDetailDto {
    List<ClubUserDto> clubUserDtoList;
}
