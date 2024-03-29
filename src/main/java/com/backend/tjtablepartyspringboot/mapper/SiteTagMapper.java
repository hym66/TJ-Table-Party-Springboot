package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.SiteTag;
import com.backend.tjtablepartyspringboot.entity.SiteType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/23
 * @JDKVersion 17.0.4
 */
@Mapper
public interface SiteTagMapper {
    @Select("SELECT * FROM site_tag")
    List<SiteTag> selectAllSiteTag();

    @Select("SELECT site_tag_name FROM site_tag WHERE site_tag_id=#{tagId}")
    String selectTagNameById(@Param("tagId") Long tagId);
}
