package com.aiia.gpt_be.history;

import com.aiia.gpt_be.IntegrationTestSupport;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class HistoryRepositoryTest extends IntegrationTestSupport {
    @Autowired
    HistoryRepository historyRepository;

    @AfterEach
    void tearDown() {
        historyRepository.deleteAllInBatch();
    }

    @DisplayName("질문 기록을 페이징해 가져올 수 있다.")
    @Test
    void getAllHistories_green() {
        // given
        Pageable pageable = PageRequest.of(0, 2);
        LocalDateTime now = LocalDateTime.of(2024, 8, 23, 0, 0, 0);

        QuestionHistory q1 = QuestionHistory.of("question1", "answer1", now);
        QuestionHistory q2 = QuestionHistory.of("question2", "answer2", now.plusDays(1));
        QuestionHistory q3 = QuestionHistory.of("question3", "answer3", now.plusDays(2));
        historyRepository.saveAll(List.of(q1, q2, q3));

        // when
        Page<QuestionHistory> pagedResults = historyRepository.getAllHistories(pageable);

        // then
        long totalCount = pagedResults.getTotalElements();
        List<QuestionHistory> result = pagedResults.getContent();

        assertThat(totalCount).isEqualTo(3);
        assertThat(result).hasSize(2)
                .extracting("question", "talkedTime")
                .containsExactlyInAnyOrder(
                        tuple("question1", now),
                        tuple("question2", now.plusDays(1))
                );
    }

    @DisplayName("질문 기록의 양이 페이지 사이즈보다 작을 경우, 전체 데이터를 가져올 수 있다.")
    @Test
    void getAllHistories_withSmallerHistorySize(){
        // given
        Pageable pageable = PageRequest.of(0, 5);
        LocalDateTime now = LocalDateTime.of(2024, 8, 23, 0, 0, 0);

        QuestionHistory q1 = QuestionHistory.of("question1", "answer1", now);
        QuestionHistory q2 = QuestionHistory.of("question2", "answer2", now.plusDays(1));
        QuestionHistory q3 = QuestionHistory.of("question3", "answer3", now.plusDays(2));
        historyRepository.saveAll(List.of(q1, q2, q3));

        // when
        Page<QuestionHistory> pagedResults = historyRepository.getAllHistories(pageable);

        // then
        long totalCount = pagedResults.getTotalElements();
        List<QuestionHistory> result = pagedResults.getContent();

        assertThat(totalCount).isEqualTo(3);
        assertThat(result).hasSize(3)
                .extracting("question", "talkedTime")
                .containsExactlyInAnyOrder(
                        tuple("question1", now),
                        tuple("question2", now.plusDays(1)),
                        tuple("question3", now.plusDays(2))
                );
    }
}
