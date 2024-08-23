package com.aiia.gpt_be.admin.repository;

import com.aiia.gpt_be.IntegrationTestSupport;
import com.aiia.gpt_be.admin.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AdminRepositoryTest extends IntegrationTestSupport {

    @Autowired
    AdminRepository adminRepository;

    @AfterEach
    void tearDown() {
        adminRepository.deleteAllInBatch();
    }

    @DisplayName("ID를 통해 관리자의 정보를 가져올 수 있다.")
    @Test
    void findByUserId_green() {
        // given
        Admin a1 = Admin.of("t1", "t1");
        Admin a2 = Admin.of("t2", "t2");
        Admin a3 = Admin.of("t3", "t3");
        adminRepository.saveAll(List.of(a1, a2, a3));

        // when
        Optional<Admin> t1 = adminRepository.findByUserId("t1");


        // then
        assertThat(t1).isPresent();
        assertThat(t1.get()).extracting("userId", "password")
                .containsExactly("t1", "t1");
    }
}