package com.aiia.gpt_be.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @GetMapping(value = {"/", "/login"})
    public String loginAdmin(@SessionAttribute(required = false, name = "admin") Admin admin){
        if(admin != null) {
            return "admin/main";
        }
        return "admin/login";
    }


}
