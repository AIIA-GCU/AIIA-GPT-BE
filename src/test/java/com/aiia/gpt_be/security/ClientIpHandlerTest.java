package com.aiia.gpt_be.security;

import com.aiia.gpt_be.IntegrationTestSupport;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ClientIpHandlerTest extends IntegrationTestSupport {

    @Mock
    HttpServletRequest servletRequest;

    @Autowired
    ClientIpHandler clientIpHandler;


    @DisplayName("클라이언트의 IP를 확인할 수 있다.")
    @Test
    void getClientIpFrom() {
        // given
        given(servletRequest.getHeader("X-Real-IP")).willReturn("203.0.113.195");

        // when
        String result = clientIpHandler.getClientIpFrom(servletRequest);

        // then
        assertThat(result).isEqualTo("203.0.113.195");
    }

    @DisplayName("X-Real-IP 없이 클라이언트의 IP를 확인할 수 있다.")
    @Test
    void getClientIpFrom_withoutXRealIP() {
        // given
        given(servletRequest.getHeader("X-Real-IP")).willReturn(null);
        given(servletRequest.getRemoteAddr()).willReturn("127.0.0.1");

        // when
        String result = clientIpHandler.getClientIpFrom(servletRequest);

        // then
        assertThat(result).isEqualTo("127.0.0.1");
    }

    @DisplayName("클라이언트의 IP를 확인할 수 없을 경우 관련 메시지를 볼 수 있다.")
    @Test
    void getClientIpFrom_withoutIP() {
        // given

        // when
        String result = clientIpHandler.getClientIpFrom(servletRequest);

        // then
        assertThat(result).isEqualTo("Unknown IP");
    }
}