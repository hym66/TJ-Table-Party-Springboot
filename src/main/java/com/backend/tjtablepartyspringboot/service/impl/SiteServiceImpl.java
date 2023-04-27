package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteTimeDto;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private SiteTagMapper siteTagMapper;

    @Autowired
    private PublicSiteTimeMapper publicSiteTimeMapper;

    @Autowired
    private PrivateSiteMapper privateSiteMapper;

    private static String weekdayTrans(int weekday) {
        if (weekday == 1) return "周一";
        else if (weekday == 2) return "周二";
        else if (weekday == 3) return "周三";
        else if (weekday == 4) return "周四";
        else if (weekday == 5) return "周五";
        else if (weekday == 6) return "周六";
        else return "周日";
    }



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

    @Override
    public PublicSiteDto selectPublicSiteById(Long publicSiteId) {
        PublicSite ps = publicSiteMapper.selectPublicSiteById(publicSiteId);
        String[] type = ps.getType().split(",");
        for (int i = 0; i < type.length; i++) {
            type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
        }
        List<PublicSiteTime> publicSiteTimes = publicSiteTimeMapper.selectTimeById(ps.getPublicSiteId());
        ArrayList<PublicSiteTimeDto> openTime = new ArrayList<>();
        for (PublicSiteTime pst : publicSiteTimes) {
            PublicSiteTimeDto publicSiteTimeDto = new PublicSiteTimeDto(weekdayTrans(pst.getWeekday()), pst.getStartTime(), pst.getEndTime());
            openTime.add(publicSiteTimeDto);
        }
        PublicSiteDto publicSiteDto = new PublicSiteDto(ps.getPublicSiteId(), ps.getCreatorId(), ps.getName(), ps.getCity(), ps.getLocation(), ps.getPicture(), ps.getIntroduction(), ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getPhone(), ps.getUploadTime(), ps.getCheckTime(), type, ps.getStatus(), openTime);
        return publicSiteDto;
    }

    @Override
    public List<SiteType> selectAllSiteType() {
        return siteTypeMapper.selectAllSiteType();
    }

    @Override
    public List<SiteTag> selectAllSiteTag() {
        return siteTagMapper.selectAllSiteTag();
    }

    @Override
    public int insertPublicSite(PublicSite publicSite) {
        return publicSiteMapper.insertPublicSite(publicSite);
    }

    @Override
    public int insertPublicSiteTime(PublicSiteTime publicSiteTime) {
        return publicSiteTimeMapper.insertPublicSiteTime(publicSiteTime);
    }

    @Override
    public int insertPrivateSite(PrivateSite privateSite) {
        return privateSiteMapper.insertPrivateSite(privateSite);
    }
}
