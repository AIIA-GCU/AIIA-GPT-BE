package com.aiia.gpt_be.admin;

import com.aiia.gpt_be.api.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String password;
}
