package com.aiia.gpt_be.admin;

import com.aiia.gpt_be.IntegrationTestSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AdminSessionManagerTest {

    @Mock
    HttpServletRequest servletRequest;
    @Mock
    HttpSession httpSession;

    @InjectMocks
    AdminSessionManager adminSessionManager;

    private static final String ADMIN_SESSION_KEY = "ADMIN";

    @BeforeEach
    void setUp() {
        given(servletRequest.getSession()).willReturn(httpSession);
    }

    @DisplayName("세션을 생성할 수 있다.")
    @Test
    void saveAdminInSession() {
        // given
        Admin admin = Admin.of("userId", "password");

        // when
        adminSessionManager.saveAdminInSession(admin, servletRequest);

        // then
        verify(httpSession).setAttribute(ADMIN_SESSION_KEY, admin);
    }

    @DisplayName("세션 내 관리자를 불러올 수 있다.")
    @Test
    void extractAdminFromSession_green() {
        // given
        Admin admin = Admin.of("userId", "password");
        given(httpSession.getAttribute(ADMIN_SESSION_KEY)).willReturn(admin);

        // when
        Optional<Admin> result = adminSessionManager.extractAdminFromSession(servletRequest);

        // then
        assertThat(admin).isEqualTo(result.get());
    }

    @DisplayName("세션 내 관리자가 없을 수 있다.")
    @Test
    void extractAdminFromSession_withoutUser() {
        // given
        given(httpSession.getAttribute(ADMIN_SESSION_KEY)).willReturn(null);

        // when
        Optional<Admin> result = adminSessionManager.extractAdminFromSession(servletRequest);

        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("관리자가 로그인했는 지 확인할 수 있다.")
    @Test
    void checkAdminLogin() {
        // given
        Admin admin = Admin.of("userId", "password");
        given(httpSession.getAttribute(ADMIN_SESSION_KEY)).willReturn(admin);

        // when
        boolean result = adminSessionManager.checkAdminLogin(servletRequest);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("관리자가 로그인하지 않았는지 확인할 수 있다.")
    @Test
    void checkAdminNotLogin() {
        // given
        given(httpSession.getAttribute(ADMIN_SESSION_KEY)).willReturn(null);

        // when
        boolean result = adminSessionManager.checkAdminNotLogin(servletRequest);

        // then
        assertThat(result).isTrue();
    }
}