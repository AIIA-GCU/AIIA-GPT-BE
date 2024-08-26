package com.aiia.gpt_be.admin.dto;

import com.aiia.gpt_be.admin.Admin;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminJoinRequest {
    @NotBlank(message = "ID는 필수입니다!")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다!")
    private String password;

    @Builder
    private AdminJoinRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static AdminJoinRequest of(String userId, String password){
        return AdminJoinRequest.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
