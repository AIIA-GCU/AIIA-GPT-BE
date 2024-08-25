package com.aiia.gpt_be.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminSessionManager {

    private static final String ADMIN_SESSION_KEY="ADMIN";


    public void saveAdminInSession(Admin admin, HttpServletRequest servletRequest) {
        servletRequest.getSession().setAttribute(ADMIN_SESSION_KEY, admin);
    }

    public Optional<Admin> extractAdminFromSession(HttpServletRequest servletRequest) {
        return Optional.ofNullable( (Admin) servletRequest.getSession().getAttribute(ADMIN_SESSION_KEY));
    }

    public boolean checkAdminLogin(HttpServletRequest servletRequest){
        return extractAdminFromSession(servletRequest).isPresent();
    }

    public boolean checkAdminNotLogin(HttpServletRequest servletRequest){
        return extractAdminFromSession(servletRequest).isEmpty();
    }
}
