package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Service
public interface MessageService {
    List<MessageDto> selectMessageInfoByUserId(String userId);

   Message selectMessageInfoById(Long messageId);

   int deleteUserViewMessage(String userId, Long messageId);

   int updateMessageView(String userId, Long messageId);

   int sendMessage(String userId, Message message);
}
