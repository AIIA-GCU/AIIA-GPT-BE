package com.aiia.gpt_be.admin;

import com.aiia.gpt_be.ControllerTestSupport;
import com.aiia.gpt_be.admin.dto.AdminLoginRequest;
import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest extends ControllerTestSupport {

    @DisplayName("로그인 페이지를 볼 수 있다.")
    @CsvSource(value = {"/admin", "/admin/", "/admin/login"})
    @ParameterizedTest(name = "{0}")
    void loginPage(String uri) throws Exception{
        // given

        // when // then
        mockMvc.perform(get(uri))
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
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/400"));
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
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/400"));
    }

    @DisplayName("메인 페이지로 접속할 수 있다.")
    @Test
    void mainPage_green() throws Exception {
        // given
        Admin admin = Admin.of("id", "password");

        // Mock page to avoid SpelException at Thymeleaf
        // If not, "EL1007E: Property or field 'totalPages' cannot be found on null" happens!
        Page mockPage = new PageImpl<>(List.of(HistoryMetaInfo.builder().build()),
                PageRequest.of(0, 5), 10);
        given(historyService.getAllHistories(any(Pageable.class))).willReturn(mockPage);

        // Mock adminSessionManager not to be intercepted by AdminLoginCheckInterceptor
        given(adminSessionManager.checkAdminLogin(any(HttpServletRequest.class))).willReturn(true);

        // when // then
        mockMvc.perform(get("/admin/main")
                        .sessionAttr("ADMIN", admin))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/main"))
                .andExpect(model().attributeExists("histories"))
                .andExpect(model().attributeExists("startPage"))
                .andExpect(model().attributeExists("endPage"));
    }
}