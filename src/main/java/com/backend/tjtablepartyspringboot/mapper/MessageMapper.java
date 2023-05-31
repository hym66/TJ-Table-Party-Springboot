package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Message;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.UserViewMessage;
import org.apache.ibatis.annotations.*;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM message WHERE message_id=${messageId}")
    Message selectMessageInfoById(@Param("messageId") Long messageId);

    @Insert("INSERT INTO message (source_id, title, content, time, type) VALUES (#{message.sourceId}, #{message.title}, #{message.content}, #{message.time}, #{message.type})")
    @Options(useGeneratedKeys = true, keyProperty = "message.messageId")
    int insertMessage(@Param("message") Message message);
}
