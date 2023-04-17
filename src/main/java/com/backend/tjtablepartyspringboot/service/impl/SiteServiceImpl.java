package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.mapper.PublicSiteMapper;
import com.backend.tjtablepartyspringboot.mapper.SiteTypeMapper;
import com.backend.tjtablepartyspringboot.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Service
public class SiteServiceImpl implements SiteService {
    @Autowired
    private PublicSiteMapper publicSiteMapper;

    @Autowired
    private SiteTypeMapper siteTypeMapper;

    @Override
    public List<PublicSiteBriefDto> selectAllPublicSite() {
        List<PublicSite> publicSites = publicSiteMapper.selectAllPublicSite();
        ArrayList<PublicSiteBriefDto> res = new ArrayList<>();
        for (PublicSite ps : publicSites) {
            String[] type = ps.getType().split(",");
            for (int i = 0; i < type.length; i++) {
                type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
            }
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum());
            res.add(publicSiteBriefDto);
        }
        return res;
    }
}
