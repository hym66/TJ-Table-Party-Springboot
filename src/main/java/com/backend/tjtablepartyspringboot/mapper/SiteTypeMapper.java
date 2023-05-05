package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.SiteType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface SiteTypeMapper {

    @Select("SELECT * FROM site_type")
    List<SiteType> selectAllSiteType();
    @Select("SELECT site_type_name FROM site_type WHERE site_type_id=#{typeId}")
    String selectTypeNameById(@Param("typeId") Long typeId);
}
