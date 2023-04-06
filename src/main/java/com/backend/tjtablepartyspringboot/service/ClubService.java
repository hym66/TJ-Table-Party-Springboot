package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.Club;
import org.springframework.stereotype.Service;

@Service
public interface ClubService {
    Club selectClub(Long clubId);
}
