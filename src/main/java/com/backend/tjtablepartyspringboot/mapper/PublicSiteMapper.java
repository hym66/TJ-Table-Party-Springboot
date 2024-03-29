package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
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
public interface PublicSiteMapper extends BaseMapper<PublicSite>{

    @Select("SELECT * FROM public_site WHERE status=1")
    List<PublicSite> selectAllPublicSite();

    @Select("SELECT * FROM public_site WHERE public_site_id=#{publicSiteId}")
    PublicSite selectPublicSiteById(@Param("publicSiteId") Long publicSiteId);

    @Select("SELECT * FROM public_site WHERE creator_id=#{creatorId}")
    List<PublicSite> selectPublicSiteByCreatorId(@Param("creatorId") String creatorId);

    @Insert("INSERT INTO public_site (creator_id, name, city, location, picture, introduction, avg_cost, capacity, phone, game_num, upload_time, status, type, tag, latitude, longitude, location_title) VALUES (#{publicSite.creatorId}, #{publicSite.name}, #{publicSite.city}, #{publicSite.location}, #{publicSite.picture}, #{publicSite.introduction}, #{publicSite.avgCost}, #{publicSite.capacity}, #{publicSite.phone}, #{publicSite.gameNum}, #{publicSite.uploadTime}, #{publicSite.status}, #{publicSite.type}, #{publicSite.tag}, #{publicSite.latitude}, #{publicSite.longitude}, #{publicSite.locationTitle})")
    @Options(useGeneratedKeys = true, keyProperty = "publicSite.publicSiteId")
    int insertPublicSite(@Param("publicSite") PublicSite publicSite);

    @Select("SELECT * FROM public_site WHERE name LIKE '%${keyword}%'")
    List<PublicSite> selectByKeyword(@Param("keyword") String keyword);

    @Update("UPDATE public_site SET name=#{publicSite.name}, city=#{publicSite.city}, location=#{publicSite.location}, picture=#{publicSite.picture}, introduction=#{publicSite.introduction}, avg_cost=#{publicSite.avgCost}, capacity=#{publicSite.capacity}, phone=#{publicSite.phone}, game_num=#{publicSite.gameNum}, upload_time=#{publicSite.uploadTime}, status=#{publicSite.status}, type=#{publicSite.type}, tag=#{publicSite.tag}, latitude=#{publicSite.latitude}, longitude=#{publicSite.longitude}, location_title=#{publicSite.locationTitle}  WHERE public_site_id='${publicSite.publicSiteId}'")
    int updatePublicSiteInfo(@Param("publicSite") PublicSite publicSite);

    @Delete("DELETE FROM public_site WHERE public_site_id=#{publicSiteId}")
    int deletePublicSite(@Param("publicSiteId") Long publicSiteId);
}
