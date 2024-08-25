package com.aiia.gpt_be.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;


@Component
public class ClientIpHandler {
    public String getClientIpFrom(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Real-IP");
        if (checkUnknownIp(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp != null ? clientIp : "Unknown IP";
    }

    private boolean checkUnknownIp(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }
}
