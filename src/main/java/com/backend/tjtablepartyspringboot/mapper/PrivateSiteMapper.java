package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.PrivateSite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface PrivateSiteMapper {

    @Insert("INSERT INTO private_site (creator_id, name, location, picture, latitude, longitude) VALUES (#{privateSite.creatorId}, #{privateSite.name}, #{privateSite.location}, #{privateSite.picture}, #{privateSite.latitude}, #{privateSite.longitude})")
    @Options(useGeneratedKeys = true, keyProperty = "privateSite.privateSiteId")
    int insertPrivateSite(@Param("privateSite") PrivateSite privateSite);

}
