package com.backend.tjtablepartyspringboot.util;

import com.backend.tjtablepartyspringboot.entity.Message;
import com.backend.tjtablepartyspringboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/5/17
 * @JDKVersion 17.0.4
 */
public class MessageUtil {
    @Autowired
    static MessageService messageService;
    static public int messageSender(String userId, Message message){
        return messageService.sendMessage(userId, message);
    }

}
