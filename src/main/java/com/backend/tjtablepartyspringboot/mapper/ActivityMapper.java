package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    //根据俱乐部id，查出已经结束的俱乐部活动
    @Select("SELECT * FROM activity WHERE club_id=#{clubId} AND NOW() > end_time")
    List<Activity> selectPastByClubId(@Param("clubId") Long clubId);

    //根据俱乐部id，查出还没结束的俱乐部活动
    @Select("SELECT * FROM activity WHERE club_id=#{clubId} AND (NOW() <= end_time OR ISNULL(end_time))")
    List<Activity> selectCurrentByClubId(@Param("clubId") Long clubId);
}
