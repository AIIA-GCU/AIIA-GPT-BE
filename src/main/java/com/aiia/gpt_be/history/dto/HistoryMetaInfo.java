package com.aiia.gpt_be.history.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryMetaInfo {

    private Long id;
    private String title;
    private String talkedTime;

    @Builder
    private HistoryMetaInfo(Long id, String title, String talkedTime) {
        this.id = id;
        this.title = title;
        this.talkedTime = talkedTime;
    }

    public static HistoryMetaInfo of(Long id, String title, String talkedTime) {
        return HistoryMetaInfo.builder()
                .id(id)
                .title(title)
                .talkedTime(talkedTime)
                .build();
    }
}
