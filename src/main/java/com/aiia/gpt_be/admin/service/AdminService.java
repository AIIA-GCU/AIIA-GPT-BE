package com.aiia.gpt_be.admin.service;

import com.aiia.gpt_be.admin.Admin;
import com.aiia.gpt_be.admin.dto.AdminLoginRequest;
import com.aiia.gpt_be.admin.repository.AdminRepository;
import com.aiia.gpt_be.admin.dto.AdminJoinRequest;
import com.aiia.gpt_be.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public Admin login(AdminLoginRequest loginRequest) {
        Admin admin = adminRepository.findByUserId(loginRequest.getUserId()).orElseThrow(() -> {
                    throw new IllegalArgumentException("ID 또는 비밀번호가 잘못되었습니다!");
                });

        if(passwordEncoder.checkInvalidPassword(admin.getPassword(), loginRequest.getPassword())) {
           throw new IllegalArgumentException("ID 또는 비밀번호가 잘못되었습니다!");
        }

        return admin;
    }

    @Transactional
    public Admin join(AdminJoinRequest joinRequest) {
        Admin newAdmin = Admin.of(joinRequest.getUserId(), passwordEncoder.encrypt(joinRequest.getPassword()));
        return adminRepository.save(newAdmin);
    }
}
