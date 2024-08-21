package com.aiia.gpt_be.admin.repository;

import com.aiia.gpt_be.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Optional<Admin> findByUserId(String userId);
}
