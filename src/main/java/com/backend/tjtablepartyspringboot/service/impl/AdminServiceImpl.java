package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.controller.AdminController;
import com.backend.tjtablepartyspringboot.dto.AdminConsoleDto;
import com.backend.tjtablepartyspringboot.dto.UserInfoDto;
import com.backend.tjtablepartyspringboot.mapper.ApplicationMapper;
import com.backend.tjtablepartyspringboot.mapper.ReportMapper;
import com.backend.tjtablepartyspringboot.service.AdminService;
import com.backend.tjtablepartyspringboot.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    ReportMapper reportMapper;

    @Autowired
    ApplicationMapper applicationMapper;

    @Autowired
    UserService userService;

    @Override
    public AdminConsoleDto getAdminConsoleDto(String adminId) {
        int uncheckedReportNum = reportMapper.selectUncheckedCount();
        int uncheckedAppNum = applicationMapper.selectUncheckedCount();
        UserInfoDto userInfo = userService.getUserInfo(adminId);

        AdminConsoleDto dto = new AdminConsoleDto(uncheckedReportNum, uncheckedAppNum, userInfo);
        return dto;
    }
}
