package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.AppDto;
import com.backend.tjtablepartyspringboot.dto.AppSimpleDto;

import java.util.List;

public interface ApplicationService {
    List<AppSimpleDto> selectUnchecked();
    AppDto selectBySiteId(Long publicSiteId);
}
