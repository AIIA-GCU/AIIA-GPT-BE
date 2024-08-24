package com.aiia.gpt_be.admin.controller;

import com.aiia.gpt_be.admin.Admin;
import com.aiia.gpt_be.admin.dto.AdminLoginRequest;
import com.aiia.gpt_be.admin.service.AdminService;
import com.aiia.gpt_be.admin.AdminSessionManager;
import com.aiia.gpt_be.admin.dto.AdminJoinRequest;
import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import com.aiia.gpt_be.history.service.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private final HistoryService historyService;

    private final AdminSessionManager adminSessionManager;

    @GetMapping(value = {"", "/", "/login"})
    public String loginPage(HttpServletRequest request) {
        if (adminSessionManager.checkAdminLogin(request)) {
            return "redirect:/admin/main";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute AdminLoginRequest loginRequest,
                        HttpServletRequest servletRequest) {
        Admin admin = adminService.login(loginRequest);
        adminSessionManager.saveAdminInSession(admin, servletRequest);
        return "redirect:/admin/main";
    }

    @GetMapping("/main")
    public String mainPage(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
        Page<HistoryMetaInfo> histories = historyService.getAllHistories(pageable);

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), histories.getTotalPages());

        model.addAttribute("histories", histories);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/main";
    }


    @PostMapping("/join")
    public String join(@Valid @ModelAttribute AdminJoinRequest joinRequest) {
        adminService.join(joinRequest);
        return "redirect:/";
    }
}
