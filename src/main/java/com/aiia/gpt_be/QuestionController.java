package com.aiia.gpt_be;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String questionPage(){
        return "homePage";
    }

    @PostMapping("/ask")
    public QuestionReply answer(@Valid @RequestBody QuestionRequest questionRequest) {
        return questionService.answer(questionRequest.to(), LocalDateTime.now());
    }

//    @PostMapping("/ask")
//    public String answer(@Valid @RequestBody QuestionRequest questionRequest, Model model) {
//        QuestionReply reply = questionService.answer(questionRequest.to(), LocalDateTime.now());
//        model.addAttribute("reply", reply);
//        return "redirect:/";
//    }
}
