package com.aiia.gpt_be.api;

import com.aiia.gpt_be.admin.controller.AdminController;
import com.aiia.gpt_be.history.controller.HistoryController;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@ControllerAdvice(assignableTypes = {
        AdminController.class,
        HistoryController.class
})
public class AdminControllerPageAdvice {

    // Extracting error message will happen at here,
    // and returning view to browser will happen at ErrorPageController

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String BAD_REQUEST_PAGE = "redirect:/error/400";
    private static final String INTERNAL_ERROR_PAGE = "redirect:/error/500";

    @ExceptionHandler(BindException.class)
    public String bindExHandler(BindException e, RedirectAttributes redirectAttributes) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String errorMessage = objectError.getDefaultMessage();

        log.info(errorMessage);

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return BAD_REQUEST_PAGE;
    }

    // ConstraintViolation : @PathVariable의 @Positive에서 -1, 0 등 값이 올 경우
    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    public String invalidInputFromUserHandler(Exception e, RedirectAttributes redirectAttributes) {
        String errorMessage = e.getMessage();

        log.info(errorMessage);

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return BAD_REQUEST_PAGE;
    }

    // Long에 String 등 잘못된 type가 올 경우
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public String methodArgumentTypeMismatchExHandler(Exception e, RedirectAttributes redirectAttributes) {
        String errorMessage = "잘못된 값입니다!";

        log.info(errorMessage);
        e.printStackTrace();

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return BAD_REQUEST_PAGE;
    }

    @ExceptionHandler(Exception.class)
    public String unExpectedExHandler(Exception e, RedirectAttributes redirectAttributes) {
        String errorMessage = e.getMessage();

        log.error(errorMessage);
        e.printStackTrace();

        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        return INTERNAL_ERROR_PAGE;
    }
}
