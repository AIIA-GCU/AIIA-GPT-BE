package com.aiia.gpt_be.admin.dto;

import com.aiia.gpt_be.admin.Admin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminLoginRequest {
    @NotBlank(message = "로그인 시 ID는 필수입니다!")
    private String userId;

    @NotBlank(message = "로그인 시 비밀번호는 필수입니다!")
    private String password;

    // Use public modifier to resolve @ModelAttribute at controller, (If protected, test will fail)
    // but please use "of" method, or other custom factory method when creating instance
    @Builder
    public AdminLoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static AdminLoginRequest of(String userId, String password){
        return AdminLoginRequest.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
