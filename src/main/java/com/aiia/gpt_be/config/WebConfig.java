package com.aiia.gpt_be.config;

import com.aiia.gpt_be.admin.AdminSessionManager;
import com.aiia.gpt_be.security.AdminLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "PATCH");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginCheckInterceptor(new AdminSessionManager()))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin", "/admin/", "/admin/login", "/ask");
    }
}
