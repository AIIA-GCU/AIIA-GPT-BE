package com.aiia.gpt_be.history.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryMetaInfo {

    private Long id;
    private String question;
    private String talkedTime;

    @Builder
    private HistoryMetaInfo(Long id, String question, String talkedTime) {
        this.id = id;
        this.question = question;
        this.talkedTime = talkedTime;
    }

    public static HistoryMetaInfo of(Long id, String question, String talkedTime) {
        return HistoryMetaInfo.builder()
                .id(id)
                .question(question)
                .talkedTime(talkedTime)
                .build();
    }
}
