package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.ClubRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClubRecordMapper extends BaseMapper<ClubRecord> {
    @Select("SELECT * FROM club_record WHERE club_id=#{clubId}")
    List<ClubRecord> selectRecordsByClubId(@Param("clubId") Long clubId);
}
