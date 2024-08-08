package com.aiia.gpt_be;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Talk extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;
    private LocalDateTime talkedTime;

    @Builder
    private Talk(String question, String answer, LocalDateTime talkedTime) {
        this.question = question;
        this.answer = answer;
        this.talkedTime = talkedTime;
    }

    public static Talk of(String question, String answer, LocalDateTime talkedTime) {
        return Talk.builder()
                .question(question)
                .answer(answer)
                .talkedTime(talkedTime)
                .build();
    }

    public QuestionReply toReply(){
        return QuestionReply.of(question, answer);
    }
}
