package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.SiteTag;
import com.backend.tjtablepartyspringboot.entity.SiteType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Service
public interface SiteService {

    List<PublicSiteBriefDto> selectAllPublicSite();

    PublicSiteDto selectPublicSiteById(Long publicSiteId);

    List<SiteType> selectAllSiteType();
    List<SiteTag> selectAllSiteTag();
}
