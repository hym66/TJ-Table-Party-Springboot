package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminConsoleDto {
    int uncheckedReportNum;
    int uncheckedAppNum;
    int uncheckedTrpgNum;
    UserInfoDto userInfo;
}
