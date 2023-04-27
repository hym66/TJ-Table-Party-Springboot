package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.ClubTrpg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClubTrpgMapper extends BaseMapper<ClubTrpg> {
    @Select("SELECT * FROM club_trpg WHERE club_id=#{clubId}")
    List<ClubTrpg> selectByClubId(@Param("clubId") Long clubId);
}
