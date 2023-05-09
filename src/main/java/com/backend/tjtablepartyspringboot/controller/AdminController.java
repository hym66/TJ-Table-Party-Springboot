package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.AdminConsoleDto;
import com.backend.tjtablepartyspringboot.dto.AppDto;
import com.backend.tjtablepartyspringboot.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Admin"})
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @ApiOperation("根据管理员id，获取管理员控制台的信息")
    @GetMapping("getAdminConsole")
    public Result<AdminConsoleDto> getAppById(@ApiParam(name="adminId", value="管理员id", required = true)
                                     @RequestParam("adminId") Long adminId)
    {
        AdminConsoleDto adminConsoleDto = adminService.getAdminConsoleDto(adminId);
        return Result.success(adminConsoleDto);
    }
}
