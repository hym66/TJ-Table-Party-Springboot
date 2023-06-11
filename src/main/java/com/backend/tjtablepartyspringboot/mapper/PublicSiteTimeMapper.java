package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.PublicSiteTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Mapper
public interface PublicSiteTimeMapper {
    @Select("SELECT weekday, start_time, end_time, open FROM public_site_time WHERE public_site_id=#{publicSiteId}")
    List<PublicSiteTime> selectTimeById(@Param("publicSiteId") Long publicSiteId);

    @Insert("INSERT INTO public_site_time (public_site_id, weekday, start_time, end_time, open) VALUES (#{publicSiteTime.publicSiteId}, #{publicSiteTime.weekday}, #{publicSiteTime.startTime}, #{publicSiteTime.endTime}, #{publicSiteTime.open})")
    int insertPublicSiteTime(@Param("publicSiteTime") PublicSiteTime publicSiteTime);

    @Update("UPDATE public_site_time SET start_time=#{publicSiteTime.startTime}, end_time=#{publicSiteTime.endTime}, open=#{publicSiteTime.open} WHERE public_site_id='${publicSiteTime.publicSiteId}' AND weekday=#{publicSiteTime.weekday}")
    int updatePublicSiteTime(@Param("publicSiteTime") PublicSiteTime publicSiteTime);

}
