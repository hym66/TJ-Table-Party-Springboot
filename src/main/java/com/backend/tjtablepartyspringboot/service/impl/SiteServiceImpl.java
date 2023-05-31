package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteTimeDto;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.SiteService;
import com.backend.tjtablepartyspringboot.service.TrpgService;
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

    @Autowired
    private SiteHasTrpgMapper siteHasTrpgMapper;

    @Autowired
    private TrpgService trpgService;

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
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getStatus());
            res.add(publicSiteBriefDto);
        }
        return res;
    }

    @Override
    public PublicSiteDto selectPublicSiteById(Long publicSiteId) {
        PublicSite ps = publicSiteMapper.selectPublicSiteById(publicSiteId);
        // 转换场地类型
        String[] type = ps.getType().split(",");
        for (int i = 0; i < type.length; i++) {
            type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
        }
        // 转换场地标签
        String[] tag = ps.getTag().split(",");
        for (int i = 0; i < tag.length; i++) {
            tag[i] = siteTagMapper.selectTagNameById(Long.valueOf(tag[i]));
        }
        // 获取场地时间信息
        List<PublicSiteTime> publicSiteTimes = publicSiteTimeMapper.selectTimeById(ps.getPublicSiteId());
        ArrayList<PublicSiteTimeDto> openTime = new ArrayList<>();
        for (PublicSiteTime pst : publicSiteTimes) {
            PublicSiteTimeDto publicSiteTimeDto = new PublicSiteTimeDto(weekdayTrans(pst.getWeekday()), pst.getStartTime(), pst.getEndTime(), pst.isOpen());
            openTime.add(publicSiteTimeDto);
        }
        // 获取场地游戏信息
        List<SiteHasTrpg> siteHasTrpgs = siteHasTrpgMapper.selectTrpgsBySite(ps.getPublicSiteId(), 0);
        List<TrpgPublic> games = new ArrayList<>();
        for (SiteHasTrpg sht: siteHasTrpgs) {
            TrpgPublic detail_public = trpgService.getDetail_public(sht.getTrpgId());
            games.add(detail_public);
        }
        // 创建dto对象
        PublicSiteDto publicSiteDto = new PublicSiteDto(ps.getPublicSiteId(), ps.getCreatorId(), ps.getName(), ps.getCity(), ps.getLocation(), ps.getPicture(), ps.getIntroduction(), ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getPhone(), ps.getUploadTime(), ps.getCheckTime(), type, tag, ps.getStatus(), openTime, ps.getLatitude(), ps.getLongitude(), games);
        return publicSiteDto;
    }

    @Override
    public List<PublicSiteBriefDto> selectPublicSiteByCreatorId(String creatorId) {
        List<PublicSite> publicSiteList = publicSiteMapper.selectPublicSiteByCreatorId(creatorId);
        ArrayList<PublicSiteBriefDto> res = new ArrayList<>();
        for (PublicSite ps : publicSiteList) {
            String[] type = ps.getType().split(",");
            for (int i = 0; i < type.length; i++) {
                type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
            }
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getStatus());
            res.add(publicSiteBriefDto);
        }
        return res;
    }

    @Override
    public List<PrivateSite> selectPrivateSiteByCreatorId(String creatorId) {
        return privateSiteMapper.selectPrivateSiteByCreatorId(creatorId);
    }

    @Override
    public PrivateSite selectPrivateSiteById(Long privateSiteId) {
        return privateSiteMapper.selectPrivateSiteById(privateSiteId);
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

    @Override
    public int modifyPrivateSite(PrivateSite privateSite) {
        return privateSiteMapper.updatePrivateSiteInfo(privateSite);
    }

    @Override
    public int deletePrivateSite(Long privateSiteId) {
        return privateSiteMapper.deletePrivateSite(privateSiteId);
    }

    @Override
    public List<PublicSite> selectByKeyword(String keyword) {
        return publicSiteMapper.selectByKeyword(keyword);
    }

    @Override
    public List<SiteHasTrpg> selectTrpgsBySite(Long siteId, int siteType) {
        return siteHasTrpgMapper.selectTrpgsBySite(siteId, siteType);
    }

    public Integer addSiteTrpg(Long siteId, String trpgId, int siteType) {
        return siteHasTrpgMapper.addSiteTrpg(siteId, trpgId, siteType);
    }
}
