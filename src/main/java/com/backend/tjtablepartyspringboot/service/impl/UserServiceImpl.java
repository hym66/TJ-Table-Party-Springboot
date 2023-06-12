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
import java.util.Date;

@Service
public class       UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDto getNameAndAvatarUrl(String userId){

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        UserDto userDto = new UserDto();
        userDto.setNickName(user.getNickName());
        userDto.setAvatarUrl(user.getAvatarUrl());
        return userDto;
    }

    @Override
    public String getRole(String userId){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        return user.getRole();
    }

    @Override
    public UserInfoDto getUserInfo(String userId){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setNickName(user.getNickName());
        userInfoDto.setAvatarUrl(user.getAvatarUrl());
        userInfoDto.setProvince(user.getProvince());
        userInfoDto.setCity(user.getCity());
        userInfoDto.setGender(user.getGender());
        userInfoDto.setRole(user.getRole());
        userInfoDto.setBantime(user.getBantime());
        Date now = new Date();
        if (user.getBantime() != null && now.before(user.getBantime())) {
            userInfoDto.setBan("yes");
        }
        else{
            userInfoDto.setBan("no");
        }
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

    @Override
    public void createUser(User userNew){
        User user =userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userNew.getUserId()));
        if (user == null) {
            user = new User();
            user.setUserId(userNew.getUserId());
            user.setNickName(userNew.getNickName());
            user.setAvatarUrl(userNew.getAvatarUrl());
            user.setRole("user");
            userMapper.insert(user);
        }
    }
}
