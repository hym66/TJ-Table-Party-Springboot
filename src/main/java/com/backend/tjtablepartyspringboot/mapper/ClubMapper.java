package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClubMapper extends BaseMapper<Club> {
    @Select("SELECT * from club WHERE club_id=#{clubId}")
    Club selectById(@Param("clubId") Long clubId);
}
