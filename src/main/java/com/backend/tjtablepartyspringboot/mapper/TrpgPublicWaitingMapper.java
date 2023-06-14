package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TrpgPublicWaitingMapper extends BaseMapper<TrpgPublicWaiting> {
    @Select("SELECT * FROM trpg_public_waiting")
    List<TrpgPublicWaiting> selectAll();
    @Select("SELECT * FROM trpg_public_waiting WHERE trpg_id=#{trpgId}")
    TrpgPublicWaiting selectByWaitingId(@Param("trpgId") String trpgId);
    @Select("SELECT COUNT(*) FROM trpg_public_waiting")
    Integer selectUncheckedCount();
}
