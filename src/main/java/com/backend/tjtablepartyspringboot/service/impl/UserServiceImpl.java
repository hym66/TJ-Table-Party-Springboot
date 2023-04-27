package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.mapper.UserMapper;
import com.backend.tjtablepartyspringboot.service.UserService;
import com.backend.tjtablepartyspringboot.entity.User;
import com.backend.tjtablepartyspringboot.dto.UserDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDto getNameAndAvatarUrl(Long userId){

        User user = userMapper.selectById(userId);
        System.out.println(user.getUserId());
        UserDto userDto = new UserDto();
        userDto.setNickName(user.getNickName());
        userDto.setAvatarUrl(user.getAvatarUrl());
        return userDto;
    }
}
