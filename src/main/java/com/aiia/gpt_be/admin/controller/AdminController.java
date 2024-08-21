package com.aiia.gpt_be.admin.controller;

import com.aiia.gpt_be.admin.Admin;
import com.aiia.gpt_be.admin.dto.AdminLoginRequest;
import com.aiia.gpt_be.admin.service.AdminService;
import com.aiia.gpt_be.admin.AdminSessionManager;
import com.aiia.gpt_be.admin.dto.AdminJoinRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminSessionManager adminSessionManager;

    @GetMapping(value = {"/", "/login"})
    public String loginPage(HttpServletRequest request){
        if(adminSessionManager.checkAdminLogin(request)) {
            return "admin/main";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute AdminLoginRequest loginRequest,
                        HttpServletRequest servletRequest) {
        Admin admin = adminService.login(loginRequest);
        adminSessionManager.saveAdminInSession(admin, servletRequest);
        return "admin/main";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute AdminJoinRequest joinRequest) {
          adminService.join(joinRequest);
          return "redirect:/";
    }
}
