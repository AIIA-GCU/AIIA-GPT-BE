package com.aiia.gpt_be;

import com.aiia.gpt_be.admin.AdminSessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockTestSupport {
    @Mock
    protected HttpServletRequest servletRequest;
    @Mock
    protected HttpSession httpSession;

    @InjectMocks
    protected AdminSessionManager adminSessionManager;

    protected static final String ADMIN_SESSION_KEY = "ADMIN";
}
