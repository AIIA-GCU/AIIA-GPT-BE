package com.aiia.gpt_be.error;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.http.HttpServletResponse.*;

@Controller
@RequestMapping("/error")
public class ErrorPageController {

    @GetMapping("/400")
    public String badRequestErrorPage(HttpServletResponse response){
        response.setStatus(SC_BAD_REQUEST);
        return "/error/400";
    }

    @GetMapping("/401")
    public String unAuthorizedErrorPage(HttpServletResponse response){
        response.setStatus(SC_UNAUTHORIZED);
        return "/error/401";
    }

    @GetMapping("/500")
    public String internalServerErrorPage(HttpServletResponse response){
        response.setStatus(SC_INTERNAL_SERVER_ERROR);
        return "/error/500";
    }
}
