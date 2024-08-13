package com.aiia.gpt_be.question;

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

//    @GetMapping("/")
//    public String questionPage(){
//        return "homePage";
//    }

//    @PostMapping("/ask")
//    public String answer(@Valid @ModelAttribute("question") QuestionRequest questionRequest, Model model) {
//        QuestionReply reply = questionService.answer(questionRequest.to(), LocalDateTime.now());
//        model.addAttribute("reply", reply);
//        return "reply";
//    }


}
