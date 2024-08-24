package com.aiia.gpt_be.security;

import com.aiia.gpt_be.admin.AdminSessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@AllArgsConstructor
public class AdminLoginCheckInterceptor implements HandlerInterceptor {
    private AdminSessionManager adminSessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return adminSessionManager.checkAdminLogin(request);
    }
}
