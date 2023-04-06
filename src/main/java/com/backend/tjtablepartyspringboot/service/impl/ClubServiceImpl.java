package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.mapper.ClubMapper;
import com.backend.tjtablepartyspringboot.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubMapper clubMapper;


    @Override
    public Club selectClub(Long clubId) {
        Club club = clubMapper.selectById(clubId);
        return club;
    }
}
