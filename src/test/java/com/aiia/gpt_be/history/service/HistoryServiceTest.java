package com.aiia.gpt_be.history.service;

import com.aiia.gpt_be.IntegrationTestSupport;
import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import com.aiia.gpt_be.history.repository.HistoryRepository;
import com.aiia.gpt_be.question.QuestionHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceTest extends IntegrationTestSupport {

    @Autowired
    HistoryService historyService;

    @Autowired
    HistoryRepository historyRepository;

    @AfterEach
    void tearDown() {
        historyRepository.deleteAllInBatch();
    }

    @DisplayName("질문 기록을 가져올 수 있다.")
    @Test
    void getAllHistories_green() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 23, 14, 0, 0);
        Pageable pageable = PageRequest.of(1, 2);

        QuestionHistory q1 = QuestionHistory.of("q1", "a1", now);
        QuestionHistory q2 = QuestionHistory.of("q2", "a2", now.plusDays(1));

        // q3, q4 will be returned
        QuestionHistory q3 = QuestionHistory.of("q3", "a3", now.plusDays(2));
        QuestionHistory q4 = QuestionHistory.of("q4", "a4", now.plusDays(3));

        historyRepository.saveAll(List.of(q1, q2, q3, q4));

        // when
        Page<HistoryMetaInfo> pagedResults = historyService.getAllHistories(pageable);

        // then
        long totalCount = pagedResults.getTotalElements();
        List<HistoryMetaInfo> results = pagedResults.getContent();

        assertThat(totalCount).isEqualTo(4);
        assertThat(results).hasSize(2)
                .extracting("question", "talkedTime")
                .containsExactlyInAnyOrder(
                        tuple("q3", "2024-08-25 14:00"),
                        tuple("q4", "2024-08-26 14:00")
                );
    }

    @DisplayName("질문 기록의 양이 페이지 사이즈보다 작을 경우, 전체 기록을 가져올 수 있다.")
    @Test
    void getAllHistories_withSmallerHistorySize() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 23, 14, 0, 0);
        Pageable pageable = PageRequest.of(0, 5);

        // All histories will be returned
        QuestionHistory q1 = QuestionHistory.of("q1", "a1", now);
        QuestionHistory q2 = QuestionHistory.of("q2", "a2", now.plusDays(1));
        QuestionHistory q3 = QuestionHistory.of("q3", "a3", now.plusDays(2));
        QuestionHistory q4 = QuestionHistory.of("q4", "a4", now.plusDays(3));

        historyRepository.saveAll(List.of(q1, q2, q3, q4));

        // when
        Page<HistoryMetaInfo> pagedResults = historyService.getAllHistories(pageable);

        // then
        long totalCount = pagedResults.getTotalElements();
        List<HistoryMetaInfo> results = pagedResults.getContent();

        assertThat(totalCount).isEqualTo(4);
        assertThat(results).hasSize(4)
                .extracting("question", "talkedTime")
                .containsExactlyInAnyOrder(
                        tuple("q1", "2024-08-23 14:00"),
                        tuple("q2", "2024-08-24 14:00"),
                        tuple("q3", "2024-08-25 14:00"),
                        tuple("q4", "2024-08-26 14:00")
                );
    }

    @DisplayName("질문 기록이 하나도 없을 경우, 빈 페이지가 전달된다.")
    @Test
    void getAllHistories_withoutHistories() {
        // given
        Pageable pageable = PageRequest.of(1, 2);

        // when
        Page<HistoryMetaInfo> pagedResults = historyService.getAllHistories(pageable);

        // then
        long totalCount = pagedResults.getTotalElements();
        List<HistoryMetaInfo> results = pagedResults.getContent();

        assertThat(totalCount).isZero();
        assertThat(results).isEmpty();
    }
}