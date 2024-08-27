package com.aiia.gpt_be.error;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.http.HttpServletResponse.*;

@Controller
@RequestMapping("/error")
public class ErrorPageController {

    private static final String ERROR_MESSAGE = "errorMessage";

    @GetMapping("/400")
    public String badRequestErrorPage(@ModelAttribute(ERROR_MESSAGE) String errorMessage,
                                      HttpServletResponse response,
                                      Model model){
        response.setStatus(SC_BAD_REQUEST);
        model.addAttribute(ERROR_MESSAGE, errorMessage);
        return "error/400";
    }

    @GetMapping("/401")
    public String unAuthorizedErrorPage(@ModelAttribute(ERROR_MESSAGE) String errorMessage,
                                        HttpServletResponse response,
                                        Model model){
        response.setStatus(SC_UNAUTHORIZED);
        model.addAttribute(ERROR_MESSAGE, errorMessage);
        return "error/401";
    }

    @GetMapping("/500")
    public String internalServerErrorPage(@ModelAttribute(ERROR_MESSAGE) String errorMessage,
                                          HttpServletResponse response,
                                          Model model){
        response.setStatus(SC_INTERNAL_SERVER_ERROR);
        model.addAttribute(ERROR_MESSAGE, errorMessage);
        return "error/500";
    }
}
