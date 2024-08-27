package com.aiia.gpt_be.error;

import com.aiia.gpt_be.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ErrorPageControllerTest extends ControllerTestSupport {

    private static final String ERROR_MESSAGE = "errorMessage";

    @DisplayName("400 페이지로 갈 수 있다.")
    @Test
    void badRequestPage() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/error/400")
                        .flashAttr(ERROR_MESSAGE, "message"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400"))
                .andExpect(model().attribute(ERROR_MESSAGE, "message"));
    }

    @DisplayName("401 페이지로 갈 수 있다.")
    @Test
    void unAuthorizedPage() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/error/401")
                        .flashAttr(ERROR_MESSAGE, "message"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(view().name("error/401"))
                .andExpect(model().attribute(ERROR_MESSAGE, "message"));
    }

    @DisplayName("500 페이지로 갈 수 있다.")
    @Test
    void internalServerPage() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/error/500")
                        .flashAttr(ERROR_MESSAGE, "message"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error/500"))
                .andExpect(model().attribute(ERROR_MESSAGE, "message"));
    }
}