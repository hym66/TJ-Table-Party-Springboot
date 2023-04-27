package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.UserDto;
import com.backend.tjtablepartyspringboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    UserDto getNameAndAvatarUrl(Long userID);
}
