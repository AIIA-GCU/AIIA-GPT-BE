package com.aiia.gpt_be.api;

import com.aiia.gpt_be.admin.controller.AdminController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice(assignableTypes = {
        AdminController.class
})
public class AdminControllerAdvice {

    private static final String ERROR_MESSAGE = "errorMessage";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public String bindExHandler(BindException e, Model model) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String errorMessage = objectError.getDefaultMessage();

        log.info(errorMessage);
        model.addAttribute(ERROR_MESSAGE, errorMessage);
        return "error/400";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExHandler(Exception e, Model model) {
        log.info(e.getMessage());
        model.addAttribute(ERROR_MESSAGE, e.getMessage());
        return "error/400";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String unExpectedExHandler(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute(ERROR_MESSAGE, e.getMessage());
        return "error/500";
    }
}
