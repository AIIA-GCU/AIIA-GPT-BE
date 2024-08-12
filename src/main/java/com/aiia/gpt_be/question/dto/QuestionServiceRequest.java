package com.aiia.gpt_be.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionServiceRequest {
    private String question;

    @Builder
    private QuestionServiceRequest(String question) {
        this.question = question;
    }

    public static QuestionServiceRequest of(String question) {
        return QuestionServiceRequest.builder()
                .question(question)
                .build();
    }
}
