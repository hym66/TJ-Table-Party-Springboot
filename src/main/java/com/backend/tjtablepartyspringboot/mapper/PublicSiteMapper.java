package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
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
public interface PublicSiteMapper {

    @Select("SELECT * FROM public_site")
    List<PublicSite> selectAllPublicSite();

    @Select("SELECT * FROM public_site WHERE public_site_id=#{publicSiteId}")
    PublicSite selectPublicSiteById(@Param("publicSiteId") Long publicSiteId);

}
