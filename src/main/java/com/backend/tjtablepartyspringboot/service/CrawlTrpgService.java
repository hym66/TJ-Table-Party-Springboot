package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.TrpgWaitingSimpleDto;
import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrawlTrpgService {
    List<TrpgWaitingSimpleDto> findAll();
    TrpgPublicWaiting findById(String trpgId);
    int addTrpgPublic(String trpgId);
    int refuseTrpgPublic(String trpgId);
    int removeTrpgPublic(String trpgId);
}
