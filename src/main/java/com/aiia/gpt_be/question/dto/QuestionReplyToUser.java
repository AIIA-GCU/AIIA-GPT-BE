package com.aiia.gpt_be.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionReplyToUser {
    private String question;
    private String answer;

    @Builder
    private QuestionReplyToUser(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public static QuestionReplyToUser of(String question, String answer){
        return QuestionReplyToUser.builder()
                .question(question)
                .answer(answer)
                .build();
    }
}
