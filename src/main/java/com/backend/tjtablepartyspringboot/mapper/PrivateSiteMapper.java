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
    List<PrivateSite> selectPrivateSiteByCreatorId(@Param("creatorId") Long creatorId);

    @Select("SELECT * FROM private_site WHERE private_site_id=#{privateSiteId}")
    PrivateSite selectPrivateSiteById(@Param("privateSiteId") Long privateSiteId);

    @Insert("INSERT INTO private_site (creator_id, name, location, picture, latitude, longitude) VALUES (#{privateSite.creatorId}, #{privateSite.name}, #{privateSite.location}, #{privateSite.picture}, #{privateSite.latitude}, #{privateSite.longitude})")
    @Options(useGeneratedKeys = true, keyProperty = "privateSite.privateSiteId")
    int insertPrivateSite(@Param("privateSite") PrivateSite privateSite);

}
