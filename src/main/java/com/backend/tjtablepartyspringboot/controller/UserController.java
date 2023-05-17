package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.service.UserService;
import com.backend.tjtablepartyspringboot.entity.User;
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

    @ApiOperation("获取该userid对应的role")
    @GetMapping("/getRole")
    public Result<String> getRole(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name="userId")  String userId
    ){
        String role=userService.getRole(userId);
        return Result.success(role);
    }
    @ApiOperation("获取该userid对应的全部信息")
    @GetMapping("/getUserInfo")
    public Result<UserInfoDto> getUserInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(name="userId")  String userId
    ){
        UserInfoDto userInfoDto =userService.getUserInfo(userId);
        return Result.success(userInfoDto);
    }
    @ApiOperation("通过前端code换取openid")
    @PostMapping("/login")
    public String login(@RequestBody String code)
    {
        return userService.login(code);
    }

    @ApiOperation("修改用户个人信息")
    @PostMapping("/updateUser")
    public Result<String> updateUser(@RequestBody(required = true) User userInfo) {
        userService.updateUser(userInfo);
        return Result.success("User information updated successfully.");
    }

    @ApiOperation("创建新用户")
    @PostMapping("/createUser")
    public Result<String> createUser(@RequestBody(required = true) User userNew) {
        userService.createUser(userNew);
        return Result.success("创建用户成功");
    }
}
