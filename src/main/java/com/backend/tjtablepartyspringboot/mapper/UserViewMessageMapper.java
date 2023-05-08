package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.UserViewMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Mapper
public interface UserViewMessageMapper {
    @Select("SELECT * FROM user_view_message WHERE user_id=${userId}")
    List<UserViewMessage> selectMessageByUserId(@Param("userId") String userId);

    @Delete("DELETE FROM user_view_message WHERE user_id=${userId} AND message_id=${messageId}")
    int deleteUserViewMessage(@Param("userId") String userId, @Param("messageId") Long messageId);

    @Update("UPDATE user_view_message SET is_view=1 WHERE user_id=${userId} AND message_id=${messageId}")
    int updateMessageView(@Param("userId") String userId, @Param("messageId") Long messageId);

}
