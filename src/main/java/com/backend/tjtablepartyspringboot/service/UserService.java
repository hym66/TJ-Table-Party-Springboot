package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    UserDto getNameAndAvatarUrl(String userId);
    UserInfoDto getUserInfo(String userId);
    String login(String code);
}
