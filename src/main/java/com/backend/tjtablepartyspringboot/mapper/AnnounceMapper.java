package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Announce;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnnounceMapper extends BaseMapper<Announce> {
    @Select("SELECT * FROM announce WHERE announce_id=#{announceId}")
    Announce selectById(@Param("announceId") Long announceId);

    @Select("SELECT * FROM announce WHERE club_id=#{clubId}")
    List<Announce> selectByClubId(@Param("clubId") Long clubId);
}
