package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.UserDto;
import com.backend.tjtablepartyspringboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"User"})
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("获取该userid对应的nickname和头像")
    @GetMapping("/getNameAndAvatarUrl")
    public Result<UserDto> getNameAndAvatarUrl(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name="userId")  String userId
    ){
            UserDto userDto =userService.getNameAndAvatarUrl(userId);
            return Result.success(userDto);
        }
}
