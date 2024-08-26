package com.aiia.gpt_be.history.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistoryInfoRequest {
    private Long id;

    public HistoryInfoRequest(Long id) {
        this.id = id;
    }
}
