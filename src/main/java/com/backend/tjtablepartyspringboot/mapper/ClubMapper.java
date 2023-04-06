package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClubMapper extends BaseMapper<Club> {
    @Select("SELECT * FROM club WHERE club_id=${clubId}")
    Club selectById(@Param("clubId") Long clubId);


}
