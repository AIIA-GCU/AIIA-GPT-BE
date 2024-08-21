package com.aiia.gpt_be.security;

import com.aiia.gpt_be.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncoderTest extends IntegrationTestSupport {

    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("문자열을 암호화할 수 있다.")
    @Test
    void encrypt_green() {
        // given
        String password = "password";

        // when
        String result = passwordEncoder.encrypt(password);

        // then
        assertThat(result).isNotEqualTo(password).hasSize(15);
    }

    @DisplayName("두 비밀번호가 같은 지 확인할 수 있다.")
    @Test
    void checkSamePassword_green() {
        // given
        String originalPassword = "password";
        String encryptedPassword = passwordEncoder.encrypt(originalPassword);
        String requestedPassword = "password";

        // when
        boolean result = passwordEncoder.checkInvalidPassword(encryptedPassword, requestedPassword);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("두 비밀번호가 다른 지 확인할 수 있다.")
    @Test
    void checkSamePassword_wrongPassword() {
        // given
        String originalPassword = "password";
        String encryptedPassword = passwordEncoder.encrypt(originalPassword);
        String wrongPassword = "wrong";

        // when
        boolean result = passwordEncoder.checkInvalidPassword(encryptedPassword, wrongPassword);

        // then
        assertThat(result).isTrue();
    }
}