package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.AdminConsoleDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    AdminConsoleDto getAdminConsoleDto(Long adminId);
}
