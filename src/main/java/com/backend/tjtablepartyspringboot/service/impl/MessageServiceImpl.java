package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.MessageDto;
import com.backend.tjtablepartyspringboot.entity.Message;
import com.backend.tjtablepartyspringboot.entity.UserViewMessage;
import com.backend.tjtablepartyspringboot.mapper.MessageMapper;
import com.backend.tjtablepartyspringboot.mapper.UserViewMessageMapper;
import com.backend.tjtablepartyspringboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserViewMessageMapper userViewMessageMapper;

    @Override
    public List<MessageDto> selectMessageInfoByUserId(Long userId) {
        List<UserViewMessage> userViewMessages = userViewMessageMapper.selectMessageByUserId(userId);
        ArrayList<MessageDto> res = new ArrayList<>();
        for (UserViewMessage uvm : userViewMessages) {
            Long messageId = uvm.getMessageId();
            Message message = messageMapper.selectMessageInfoById(messageId);
            MessageDto messageDto = new MessageDto(message.getMessageId(), message.getSourceId(), message.getTitle(), message.getContent(), message.getTime(), message.getType(), uvm.getIsView());
            res.add(messageDto);
        }
        return res;
    }

    @Override
    public Message selectMessageInfoById(Long messageId) {
        return messageMapper.selectMessageInfoById(messageId);
    }

    @Override
    public int deleteUserViewMessage(Long userId, Long messageId) {
        return userViewMessageMapper.deleteUserViewMessage(userId, messageId);
    }

    @Override
    public int updateMessageView(Long userId, Long messageId) {
        return userViewMessageMapper.updateMessageView(userId, messageId);
    }
}
