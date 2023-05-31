package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.PrivateSite;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface PrivateSiteMapper {

    @Select("SELECT * FROM private_site WHERE creator_id=#{creatorId}")
    List<PrivateSite> selectPrivateSiteByCreatorId(@Param("creatorId") String creatorId);

    @Select("SELECT * FROM private_site WHERE private_site_id=#{privateSiteId}")
    PrivateSite selectPrivateSiteById(@Param("privateSiteId") Long privateSiteId);

    @Insert("INSERT INTO private_site (creator_id, name, location, picture, latitude, longitude, location_title) VALUES (#{privateSite.creatorId}, #{privateSite.name}, #{privateSite.location}, #{privateSite.picture}, #{privateSite.latitude}, #{privateSite.longitude}, #{privateSite.locationTitle})")
    @Options(useGeneratedKeys = true, keyProperty = "privateSite.privateSiteId")
    int insertPrivateSite(@Param("privateSite") PrivateSite privateSite);

    @Delete("DELETE FROM private_site WHERE private_site_id=#{privateSiteId}")
    int deletePrivateSite(@Param("privateSiteId") Long privateSiteId);

    @Update("UPDATE private_site SET name=#{privateSite.name}, location=#{privateSite.location}, picture=#{privateSite.picture}, latitude=#{privateSite.latitude}, longitude=#{privateSite.longitude}, location_title=#{privateSite.locationTitle}  WHERE private_site_id='${privateSite.privateSiteId}'")
    int updatePrivateSiteInfo(@Param("privateSite") PrivateSite privateSite);

}
