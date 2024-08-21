package com.aiia.gpt_be.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class PasswordEncoder {

    @Value("${security.salt}")
    private String salt;

    public String encrypt(String password) {
        try {

            // 1. SHA256 알고리즘 객체 생성
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");

            // 2. password와 salt 합친 문자열에 SHA-256 알고리즘 적용
            md.update((password+salt).getBytes());
            byte[] pwdsalt = md.digest();

            // 3. Encrypt된 결과를 문자열로 변환
            StringBuilder sb = new StringBuilder();
            for(byte b : pwdsalt) {
                sb.append(String.format("%02x", b));
            }

            return sb.substring(0, 15);

        } catch (Exception e) {
            e.printStackTrace();
            return "Security error occurs";
        }
    }

    public boolean checkInvalidPassword(String encryptedPassword, String requestedPassword) {
        return !encryptedPassword.equals(encrypt(requestedPassword));
    }
}
