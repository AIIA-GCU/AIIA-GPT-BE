package com.aiia.gpt_be;

import com.aiia.gpt_be.admin.AdminSessionManager;
import com.aiia.gpt_be.admin.controller.AdminController;
import com.aiia.gpt_be.admin.service.AdminService;
import com.aiia.gpt_be.error.ErrorPageController;
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
}
