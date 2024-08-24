package com.aiia.gpt_be.security;

import com.aiia.gpt_be.admin.AdminSessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AdminLoginCheckInterceptor implements HandlerInterceptor {
    private final AdminSessionManager adminSessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return adminSessionManager.checkAdminLogin(request);
    }
}
