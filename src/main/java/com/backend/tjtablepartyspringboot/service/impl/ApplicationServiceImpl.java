package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.AppDto;
import com.backend.tjtablepartyspringboot.dto.AppSimpleDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteTimeDto;
import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.backend.tjtablepartyspringboot.entity.PublicSiteTime;
import com.backend.tjtablepartyspringboot.mapper.ApplicationMapper;
import com.backend.tjtablepartyspringboot.mapper.PublicSiteMapper;
import com.backend.tjtablepartyspringboot.mapper.PublicSiteTimeMapper;
import com.backend.tjtablepartyspringboot.mapper.SiteTypeMapper;
import com.backend.tjtablepartyspringboot.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    SiteTypeMapper siteTypeMapper;
    @Autowired
    PublicSiteTimeMapper publicSiteTimeMapper;
    @Autowired
    PublicSiteMapper publicSiteMapper;

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
    public List<AppSimpleDto> selectUnchecked() {
        List<PublicSite> publicSiteList = applicationMapper.selectUnchecked();
        List<AppSimpleDto> dtoList = publicSiteList.stream().map(site -> new AppSimpleDto(site)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public AppDto selectBySiteId(Long publicSiteId) {
        PublicSite ps = applicationMapper.selectById(publicSiteId);
        if(ps == null){
            return null;
        }
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

        AppDto appDto = new AppDto(ps, type, openTime);
        return appDto;
    }

    @Override
    public int adminCheck(Long publicSiteId, boolean agree, Long adminId) {
        Byte status = agree ?  Byte.valueOf("1") : Byte.valueOf("0");

        PublicSite publicSite = publicSiteMapper.selectPublicSiteById(publicSiteId);
        publicSite.setCheckTime(new Date());
        publicSite.setStatus(status);
        publicSite.setAdminId(adminId);
        int res = publicSiteMapper.updateById(publicSite);
        return res;
    }
}