package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.TrpgWaitingSimpleDto;
import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.backend.tjtablepartyspringboot.mapper.TrpgPublicWaitingMapper;
import com.backend.tjtablepartyspringboot.service.CrawlTrpgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrawlTrpgServiceImpl implements CrawlTrpgService {
    @Autowired
    TrpgPublicWaitingMapper trpgPublicWaitingMapper;

    @Override
    public List<TrpgWaitingSimpleDto> findAll() {
        List<TrpgPublicWaiting> list = trpgPublicWaitingMapper.selectAll();
        List<TrpgWaitingSimpleDto> dtos = list.stream()
                                                .map(entity -> new TrpgWaitingSimpleDto(entity))
                                                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public TrpgPublicWaiting findById(String trpgId) {
        TrpgPublicWaiting trpgPublicWaiting = trpgPublicWaitingMapper.selectByWaitingId(trpgId);
        return null;
    }
}
