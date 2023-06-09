package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.PrivateSite;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface PrivateSiteMapper extends BaseMapper<PrivateSite> {

    @Select("SELECT * FROM private_site WHERE creator_id=#{creatorId}")
    List<PrivateSite> selectPrivateSiteByCreatorId(@Param("creatorId") String creatorId);

    @Select("SELECT * FROM private_site WHERE private_site_id=#{privateSiteId}")
    PrivateSite selectPrivateSiteById(@Param("privateSiteId") Long privateSiteId);

    @Insert("INSERT INTO private_site (creator_id, name, city, location, picture, latitude, longitude, location_title, game_num) VALUES (#{privateSite.creatorId}, #{privateSite.name}, #{privateSite.city}, #{privateSite.location}, #{privateSite.picture}, #{privateSite.latitude}, #{privateSite.longitude}, #{privateSite.locationTitle}, #{privateSite.gameNum})")
    @Options(useGeneratedKeys = true, keyProperty = "privateSite.privateSiteId")
    int insertPrivateSite(@Param("privateSite") PrivateSite privateSite);

    @Delete("DELETE FROM private_site WHERE private_site_id=#{privateSiteId}")
    int deletePrivateSite(@Param("privateSiteId") Long privateSiteId);

    @Update("UPDATE private_site SET name=#{privateSite.name}, city=#{privateSite.city}, location=#{privateSite.location}, picture=#{privateSite.picture}, latitude=#{privateSite.latitude}, longitude=#{privateSite.longitude}, location_title=#{privateSite.locationTitle}, game_num=#{privateSite.gameNum} WHERE private_site_id='${privateSite.privateSiteId}'")
    int updatePrivateSiteInfo(@Param("privateSite") PrivateSite privateSite);

}
