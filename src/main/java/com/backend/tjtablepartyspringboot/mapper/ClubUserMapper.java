package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.ClubUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClubUserMapper extends BaseMapper<ClubUser> {
    @Select("SELECT * FROM club_user WHERE club_id=#{clubId}")
    List<ClubUser> selectUsersByClubId(@Param("clubId") Long clubId);
    @Delete("DELETE FROM club_user WHERE club_id=#{clubId} AND user_id=#{userId}")
    int deleteUser(@Param("clubId") Long clubId, @Param("userId") String userId);

}
