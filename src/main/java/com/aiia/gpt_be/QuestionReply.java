package com.aiia.gpt_be;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionReply {
    private String question;
    private String answer;

    @Builder
    private QuestionReply(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public static QuestionReply of(String question, String answer){
        return QuestionReply.builder()
                .question(question)
                .answer(answer)
                .build();
    }
}
