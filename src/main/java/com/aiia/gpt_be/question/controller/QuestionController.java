package com.aiia.gpt_be.question.controller;

import com.aiia.gpt_be.question.service.QuestionService;
import com.aiia.gpt_be.question.dto.QuestionReply;
import com.aiia.gpt_be.question.dto.QuestionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/ask")
    public QuestionReply answer(@Valid @RequestBody QuestionRequest questionRequest) {
        return questionService.answer(questionRequest.to(), LocalDateTime.now());
    }
}
