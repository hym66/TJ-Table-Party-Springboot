package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.TrpgWaitingSimpleDto;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.backend.tjtablepartyspringboot.mapper.TrpgPublicMapper;
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
    @Autowired
    TrpgPublicMapper trpgPublicMapper;

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
        return trpgPublicWaiting;
    }

    @Override
    public int addTrpgPublic(String trpgId) {
        TrpgPublicWaiting trpgPublicWaiting = trpgPublicWaitingMapper.selectByWaitingId(trpgId);
        //把waiting表里的游戏删掉
        int res = trpgPublicWaitingMapper.deleteById(trpgId);

        //加到trpg表里
        TrpgPublic trpgPublic = new TrpgPublic(trpgPublicWaiting);
        res = trpgPublicMapper.insert(trpgPublic);
        return res;
    }

    @Override
    public int refuseTrpgPublic(String trpgId) {
        //把waiting表里的游戏删掉
        int res = trpgPublicWaitingMapper.deleteById(trpgId);
        return res;
    }

    @Override
    public int removeTrpgPublic(String trpgId) {
        //把waiting表里的游戏删掉
        int res = trpgPublicWaitingMapper.deleteById(trpgId);
        return res;
    }

    @Override
    public int selectUncheckedCount() {
        int res = trpgPublicWaitingMapper.selectUncheckedCount();
        return res;
    }
}
