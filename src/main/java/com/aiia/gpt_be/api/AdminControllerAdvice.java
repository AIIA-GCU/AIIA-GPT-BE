package com.aiia.gpt_be.api;

import com.aiia.gpt_be.admin.controller.AdminController;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@ControllerAdvice(assignableTypes = {
        AdminController.class
})
public class AdminControllerAdvice {

    // Extracting error message will happen at here,
    // and returning view to browser will happen at ErrorPageController

    private static final String ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler(BindException.class)
    public String bindExHandler(BindException e, RedirectAttributes redirectAttributes) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String errorMessage = objectError.getDefaultMessage();

        log.info(errorMessage);

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return "redirect:/error/400";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExHandler(Exception e, RedirectAttributes redirectAttributes) {
        String errorMessage = e.getMessage();

        log.info(errorMessage);

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return "redirect:/error/400";
    }

    @ExceptionHandler(Exception.class)
    public String unExpectedExHandler(Exception e, RedirectAttributes redirectAttributes) {
        String errorMessage = e.getMessage();

        log.error(errorMessage);
        e.printStackTrace();

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return "redirect:/error/500";
    }
}
