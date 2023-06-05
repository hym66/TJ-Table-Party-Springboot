package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.ClubUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClubUserMapper extends BaseMapper<ClubUser> {
    @Select("SELECT * FROM club_user WHERE club_id=#{clubId} AND status=1")
    List<ClubUser> selectUsersByClubId(@Param("clubId") Long clubId);
    @Delete("DELETE FROM club_user WHERE club_id=#{clubId} AND user_id=#{userId}")
    int deleteUser(@Param("clubId") Long clubId, @Param("userId") String userId);
    @Select("SELECT * FROM club_user WHERE club_id=#{clubId} AND status=0")
    List<ClubUser> selectWaitingUsers(@Param("clubId") Long clubId);
    @Update("UPDATE club_user SET status=0 WHERE club_id=#{clubId} AND user_id=#{userId}")
    int activateClubUser(@Param("clubId") Long clubId, @Param("userId") String userId);

    @Select("SELECT * FROM club_user WHERE club_id=#{clubId} AND user_id=#{userId}")
    ClubUser selectClubUserById(@Param("clubId") Long clubId, @Param("userId") String userId);

}
