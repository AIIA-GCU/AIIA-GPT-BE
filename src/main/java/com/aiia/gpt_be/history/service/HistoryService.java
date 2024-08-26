package com.aiia.gpt_be.history.service;

import com.aiia.gpt_be.history.dto.HistoryInfo;
import com.aiia.gpt_be.history.dto.HistoryInfoRequest;
import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import com.aiia.gpt_be.history.repository.HistoryRepository;
import com.aiia.gpt_be.question.QuestionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

    public Page<HistoryMetaInfo> getAllHistories(Pageable pageable) {
        return historyRepository.getAllHistories(pageable)
                .map(QuestionHistory::toHistoryMetaInfo);
    }

    public HistoryInfo getHistory(HistoryInfoRequest request) {
        return historyRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("기록을 찾을 수 없습니다!"))
                .toHistoryInfo();
    }
}
