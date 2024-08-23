package com.aiia.gpt_be.admin;

import com.aiia.gpt_be.ControllerTestSupport;
import com.aiia.gpt_be.admin.dto.AdminLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest extends ControllerTestSupport {

    @DisplayName("로그인 페이지를 볼 수 있다.")
    @Test
    void loginPage_green1() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/login"));
    }

    @DisplayName("로그인 페이지를 볼 수 있다.")
    @Test
    void loginPage_green2() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/admin/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/login"));
    }

    @DisplayName("로그인 페이지를 볼 수 있다.")
    @Test
    void loginPage_green3() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/admin/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/login"));
    }

    @DisplayName("이미 로그인되었다면, 메인 페이지를 볼 수 있다.")
    @Test
    void loginPage_withSession() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");
        given(adminSessionManager.checkAdminLogin(any(HttpServletRequest.class))).willReturn(true);

        // when // then
        mockMvc.perform(get("/admin/")
                        .sessionAttr("admin", admin))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/main"));
    }

    @DisplayName("로그인된 상태로 다시 로그인 페이지에 접속해도 메인 페이지를 볼 수 있다.")
    @Test
    void loginPage_withSessionAndLoginPage() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");
        given(adminSessionManager.checkAdminLogin(any(HttpServletRequest.class))).willReturn(true);

        // when // then
        mockMvc.perform(get("/admin/login")
                        .sessionAttr("admin", admin))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/main"));
    }

    @DisplayName("로그인할 수 있다.")
    @Test
    void login_green() throws Exception {
        // given
        given(adminService.login(any(AdminLoginRequest.class))).willReturn(Admin.of("userId", "password"));

        // when // then
        mockMvc.perform(post("/admin/login")
                        .param("userId", "userId")
                        .param("password", "password")
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/main"));

        verify(adminSessionManager).saveAdminInSession(any(Admin.class), any(HttpServletRequest.class));
    }

    @DisplayName("로그인 시 ID는 필수이다.")
    @Test
    void login_blankUserId() throws Exception {
        // given

        // when // then
        mockMvc.perform(post("/admin/login")
                        .param("password", "password")
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400"))
                .andExpect(model().attribute("errorMessage", "로그인 시 ID는 필수입니다!"));
    }

    @DisplayName("로그인 시 비밀번호는 필수이다.")
    @Test
    void login_blankPassword() throws Exception {
        // given

        // when // then
        mockMvc.perform(post("/admin/login")
                        .param("userId", "userId")
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400"))
                .andExpect(model().attribute("errorMessage", "로그인 시 비밀번호는 필수입니다!"));
    }

    @DisplayName("메인 페이지로 접속할 수 있다.")
    @Test
    void mainPage_green() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");
        given(adminSessionManager.checkAdminLogin(any(HttpServletRequest.class))).willReturn(true);

        // when // then
        mockMvc.perform(get("/admin/main"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/main"));
    }
}