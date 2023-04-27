package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClubMapper extends BaseMapper<Club> {
    @Select("SELECT * FROM club WHERE club_id=#{clubId}")
    Club selectById(@Param("clubId") Long clubId);

    //查找某个城市的所有俱乐部
    @Select("SELECT * FROM club WHERE city=#{city}")
    List<Club> selectByCity(@Param("city") String city);

    //查找某个用户参与的所有俱乐部
    @Select("SELECT * FROM club INNER JOIN club_user USING(club_id) WHERE user_id=#{userId}")
    List<Club> selectUserClubs(@Param("userId") Long userId);

    //查找俱乐部当前人数
    @Select("SELECT COUNT(user_id) FROM club_user WHERE club_id=#{clubId}")
    Integer selectClubPersonNum(@Param("clubId") Long clubId);
}
