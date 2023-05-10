package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.mapper.UserMapper;
import com.backend.tjtablepartyspringboot.service.UserService;
import com.backend.tjtablepartyspringboot.entity.User;
import com.backend.tjtablepartyspringboot.dto.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDto getNameAndAvatarUrl(String userId){

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        System.out.println(user.getUserId());
        UserDto userDto = new UserDto();
        userDto.setNickName(user.getNickName());
        userDto.setAvatarUrl(user.getAvatarUrl());
        return userDto;
    }

    @Override
    public UserInfoDto getUserInfo(String userId){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        System.out.println(user.getUserId());
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setNickName(user.getNickName());
        userInfoDto.setAvatarUrl(user.getAvatarUrl());
        userInfoDto.setProvince(user.getProvince());
        userInfoDto.setCity(user.getCity());
        userInfoDto.setGender(user.getGender());
        return userInfoDto;
    }
    @Override
    public String login(String code) {
        System.out.println(code);
        // 调用微信接口获取openid
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        url = url.replace("APPID", "wx0336be367b93151a")
                .replace("SECRET", "edadb5341949dffa2c079f9d56b44b18")
                .replace("JSCODE", code);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        // 解析json获取openid
        System.out.println(response);
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.containsKey("openid")) {
            String openid = jsonObject.getString("openid");
            return openid;
        } else {
            return null;
        }
    }

    @Override
    public void updateUser(User userInfo){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userInfo.getUserId()));
        if (user == null) {
            throw new RuntimeException("User not found.");
        }
        user.setNickName(userInfo.getNickName());
        user.setAvatarUrl(userInfo.getAvatarUrl());
        user.setProvince(userInfo.getProvince());
        user.setCity(userInfo.getCity());
        user.setGender(userInfo.getGender());
        userMapper.updateById(user);
    }
}
