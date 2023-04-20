package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.UserViewMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/20
 * @JDKVersion 17.0.4
 */
@Mapper
public interface UserViewMessageMapper {
    @Select("SELECT * FROM user_view_message WHERE user_id=${userId}")
    List<UserViewMessage> selectMessageByUserId(@Param("userId") Long userId);
}
