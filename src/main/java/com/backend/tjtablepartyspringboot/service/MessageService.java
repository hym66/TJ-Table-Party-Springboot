package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Service
public interface MessageService {
    List<MessageDto> selectMessageInfoByUserId(Long userId);
}
