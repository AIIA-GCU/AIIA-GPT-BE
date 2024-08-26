package com.aiia.gpt_be.history.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistoryInfo {

    private String question;
    private String answer;
    private String talkedTime;

    @Builder
    private HistoryInfo(String question, String answer, String talkedTime) {
        this.question = question;
        this.answer = answer;
        this.talkedTime = talkedTime;
    }

    public static HistoryInfo of(String question, String answer, String talkedTime) {
        return HistoryInfo.builder()
                .question(question)
                .answer(answer)
                .talkedTime(talkedTime)
                .build();
    }
}
