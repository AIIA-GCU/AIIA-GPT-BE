package com.aiia.gpt_be.history.controller;

import com.aiia.gpt_be.ControllerTestSupport;
import com.aiia.gpt_be.admin.Admin;
import com.aiia.gpt_be.history.dto.HistoryInfo;
import com.aiia.gpt_be.history.dto.HistoryInfoRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HistoryControllerTest extends ControllerTestSupport {


    @DisplayName("사용자의 질문 기록을 가져올 수 있다.")
    @Test
    void getHistory_green() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");
        given(adminSessionManager.checkAdminNotLogin(any(HttpServletRequest.class)))
                .willReturn(false);

        given(historyService.getHistory(any(HistoryInfoRequest.class)))
                .willReturn(HistoryInfo.of("q1", "a1", "time"));

        // when // then
        mockMvc.perform(get("/history/1")
                        .sessionAttr(ADMIN_SESSION_KEY, admin))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("history/view"))
                .andExpect(model().attributeExists("history"));
    }

//    @DisplayName("질문 기록을 가져올 때, 잘못된 접근을 확인할 수 있다.")
//    @CsvSource(value = {"0", "-1", "abcde", "ㄱㄴㄷㄹ", "!@#$%"})
//    @ParameterizedTest(name = "{0}")
//    void getHistory_invalidID(String invalidPath) throws Exception {
//        // given
//        Admin admin = Admin.of("id", "password");
//        given(adminSessionManager.checkAdminNotLogin(any(HttpServletRequest.class)))
//                .willReturn(false);
//
//        // when // then
//        mockMvc.perform(get("/history/" + invalidPath)
//                        .sessionAttr(ADMIN_SESSION_KEY, admin))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/error/400"))
//                .andExpect(flash().attribute(ERROR_MESSAGE, "잘못된 값입니다!"));
//    }
}