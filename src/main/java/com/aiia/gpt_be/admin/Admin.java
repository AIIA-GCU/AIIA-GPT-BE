package com.aiia.gpt_be.admin;

import com.aiia.gpt_be.api.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String password;

    @Builder
    private Admin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static Admin of(String userId, String password) {
        return Admin.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
