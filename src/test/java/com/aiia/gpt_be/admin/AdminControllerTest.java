package com.aiia.gpt_be.admin;

import com.aiia.gpt_be.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest extends ControllerTestSupport {

    @DisplayName("로그인 페이지를 볼 수 있다.")
    @Test
    void loginAdmin_green() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/admin/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/login"));
    }

    @DisplayName("이미 로그인되었다면, 메인 페이지를 볼 수 있다.")
    @Test
    void loginAdmin_withSession() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");

        // when // then
        mockMvc.perform(get("/admin/")
                        .sessionAttr("admin", admin))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/main"));
    }

    @DisplayName("로그인된 상태로 다시 로그인 페이지에 접속해도 메인 페이지를 볼 수 있다.")
    @Test
    void loginAdmin_withSessionAndLoginPage() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");

        // when // then
        mockMvc.perform(get("/admin/login")
                        .sessionAttr("admin", admin))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/main"));
    }
}