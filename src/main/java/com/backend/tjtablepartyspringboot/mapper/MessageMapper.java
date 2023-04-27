package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Message;
import com.backend.tjtablepartyspringboot.entity.UserViewMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM message WHERE message_id=${messageId}")
    Message selectMessageInfoById(@Param("messageId") Long messageId);
}
