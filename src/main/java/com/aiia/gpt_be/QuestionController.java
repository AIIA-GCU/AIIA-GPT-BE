package com.aiia.gpt_be;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String questionPage(){
        return "homePage";
    }

    @PostMapping("/ask")
    public String answer(@Valid @ModelAttribute("question") QuestionRequest questionRequest, Model model) {
        QuestionReply reply = questionService.answer(questionRequest.to(), LocalDateTime.now());
        model.addAttribute("reply", reply);
        return "reply";
    }

//    @PostMapping("/ask")
//    public QuestionReply answer(@Valid @RequestBody QuestionRequest questionRequest) {
//        return questionService.answer(questionRequest.to(), LocalDateTime.now());
//    }
}
