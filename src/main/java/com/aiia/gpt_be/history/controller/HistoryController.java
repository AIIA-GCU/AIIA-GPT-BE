package com.aiia.gpt_be.history.controller;

import com.aiia.gpt_be.history.dto.HistoryInfo;
import com.aiia.gpt_be.history.dto.HistoryInfoRequest;
import com.aiia.gpt_be.history.service.HistoryService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{id}")
    public String getHistory(@Positive(message = "잘못된 값입니다!") @PathVariable Long id,
                             Model model){
        HistoryInfo history = historyService.getHistory(new HistoryInfoRequest(id));
        model.addAttribute("history", history);
        return "/history/view";
    }
}
