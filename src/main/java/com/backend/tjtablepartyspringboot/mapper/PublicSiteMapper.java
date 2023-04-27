package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface PublicSiteMapper {

    @Select("SELECT * FROM public_site")
    List<PublicSite> selectAllPublicSite();

    @Select("SELECT * FROM public_site WHERE public_site_id=#{publicSiteId}")
    PublicSite selectPublicSiteById(@Param("publicSiteId") Long publicSiteId);

    @Insert("INSERT INTO public_site (creator_id, name, city, location, picture, introduction, avg_cost, capacity, phone, game_num, upload_time, status, type, tag, latitude, longitude) VALUES (#{publicSite.creatorId}, #{publicSite.name}, #{publicSite.city}, #{publicSite.location}, #{publicSite.picture}, #{publicSite.introduction}, #{publicSite.avgCost}, #{publicSite.capacity}, #{publicSite.phone}, #{publicSite.gameNum}, #{publicSite.uploadTime}, #{publicSite.status}, #{publicSite.type}, #{publicSite.tag}, #{publicSite.latitude}, #{publicSite.longitude})")
    @Options(useGeneratedKeys = true, keyProperty = "publicSite.publicSiteId")
    int insertPublicSite(@Param("publicSite") PublicSite publicSite);

}
