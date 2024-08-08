package com.aiia.gpt_be;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "질문을 입력해주세요!")
    private String question;

    @Builder
    private QuestionRequest(String question) {
        this.question = question;
    }

    public static QuestionRequest of(String question){
        return QuestionRequest.builder()
                .question(question)
                .build();
    }

    public QuestionServiceRequest to(){
        return QuestionServiceRequest.of(question);
    }
}
