package com.aiia.gpt_be.admin.service;

import com.aiia.gpt_be.IntegrationTestSupport;
import com.aiia.gpt_be.admin.Admin;
import com.aiia.gpt_be.admin.dto.AdminLoginRequest;
import com.aiia.gpt_be.admin.repository.AdminRepository;
import com.aiia.gpt_be.security.PasswordEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest extends IntegrationTestSupport {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        adminRepository.deleteAllInBatch();
    }

    @DisplayName("로그인할 수 있다.")
    @Test
    void login_green() {
        // given
        AdminLoginRequest request = AdminLoginRequest.of("user1", "p1");
        Admin a1 = Admin.of("user1", passwordEncoder.encrypt("p1"));
        Admin a2 = Admin.of("user2", passwordEncoder.encrypt("p2"));
        Admin a3 = Admin.of("user3", passwordEncoder.encrypt("p3"));
        adminRepository.saveAll(List.of(a1, a2, a3));

        // when
        Admin result = adminService.login(request);

        // then
        assertThat(result).extracting("userId", "password")
                .containsExactly("user1", passwordEncoder.encrypt("p1"));
    }

    @DisplayName("ID가 다를 경우 로그인할 수 없다.")
    @Test
    void login_invalidUserId() {
        // given
        AdminLoginRequest request = AdminLoginRequest.of("WRONG", "p1");
        Admin a1 = Admin.of("user1", passwordEncoder.encrypt("p1"));
        Admin a2 = Admin.of("user2", passwordEncoder.encrypt("p2"));
        Admin a3 = Admin.of("user3", passwordEncoder.encrypt("p3"));
        adminRepository.saveAll(List.of(a1, a2, a3));

        // when // then
        assertThatThrownBy(() -> adminService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID 또는 비밀번호가 잘못되었습니다!");
    }

    @DisplayName("비밀번호가 다를 경우 로그인할 수 없다.")
    @Test
    void login_invalidPassword() {
        // given
        AdminLoginRequest request = AdminLoginRequest.of("user1", "WRONG");
        Admin a1 = Admin.of("user1", passwordEncoder.encrypt("p1"));
        Admin a2 = Admin.of("user2", passwordEncoder.encrypt("p2"));
        Admin a3 = Admin.of("user3", passwordEncoder.encrypt("p3"));
        adminRepository.saveAll(List.of(a1, a2, a3));

        // when // then
        assertThatThrownBy(() -> adminService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID 또는 비밀번호가 잘못되었습니다!");
    }
}