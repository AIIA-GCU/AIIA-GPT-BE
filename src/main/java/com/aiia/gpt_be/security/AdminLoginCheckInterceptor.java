package com.aiia.gpt_be.security;

import com.aiia.gpt_be.admin.AdminSessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;

import static jakarta.servlet.http.HttpServletResponse.*;

@Slf4j
@AllArgsConstructor
public class AdminLoginCheckInterceptor implements HandlerInterceptor {
    private AdminSessionManager adminSessionManager;
    private ClientIpHandler clientIpHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        boolean doesAdminNotLoggedIn = adminSessionManager.checkAdminNotLogin(request);
        if(doesAdminNotLoggedIn) {
            String clientIp = clientIpHandler.getClientIpFrom(request);

            log.warn("Unauthorized access !!! Client IP = {}, Detected time = {}", clientIp, LocalDateTime.now());

            response.sendRedirect("/error/401");
            return false;
        }
        return true;
    }
}
