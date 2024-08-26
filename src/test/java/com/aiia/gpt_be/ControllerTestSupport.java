package com.aiia.gpt_be;

import com.aiia.gpt_be.admin.AdminSessionManager;
import com.aiia.gpt_be.admin.controller.AdminController;
import com.aiia.gpt_be.admin.service.AdminService;
import com.aiia.gpt_be.error.ErrorPageController;
import com.aiia.gpt_be.history.controller.HistoryController;
import com.aiia.gpt_be.history.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = {
        AdminController.class,
        HistoryController.class,
        ErrorPageController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AdminService adminService;

    @MockBean
    protected HistoryService historyService;

    @MockBean
    protected AdminSessionManager adminSessionManager;

    protected static final String ADMIN_SESSION_KEY="ADMIN";
    protected static final String ERROR_MESSAGE = "errorMessage";
}
