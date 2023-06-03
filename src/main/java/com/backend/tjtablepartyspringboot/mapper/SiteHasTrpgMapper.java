package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.SiteHasTrpg;
import com.backend.tjtablepartyspringboot.entity.SiteTag;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/5/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface SiteHasTrpgMapper {
    @Select("SELECT * FROM site_has_trpg WHERE site_id=#{siteId} AND site_type=#{siteType}")
    List<SiteHasTrpg> selectTrpgsBySite(@Param("siteId") Long siteId, @Param("siteType") int siteType);

    @Insert("INSERT INTO site_has_trpg (site_id, trpg_id, site_type) VALUES (#{siteId}, #{trpgId}, #{siteType})")
    Integer addSiteTrpg(@Param("siteId") Long siteId, @Param("trpgId") String trpgId, @Param("siteType") int siteType);

    @Delete("DELETE FROM site_has_trpg WHERE site_id=#{siteId} and site_type=#{siteType}")
    Integer deleteSiteTrpg(@Param("siteId") Long siteId, @Param("siteType") int siteType);
}
