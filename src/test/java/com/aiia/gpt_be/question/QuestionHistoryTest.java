package com.aiia.gpt_be.question;

import com.aiia.gpt_be.IntegrationTestSupport;
import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class QuestionHistoryTest extends IntegrationTestSupport {

    @DisplayName("HTML에 전달할 데이터 양식으로 변환할 수 있다.")
    @Test
    void toHistoryMetaInfo_green() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 23, 0, 0, 0);

        QuestionHistory q1 = makeQuestionHistory(1L, "q1", "a1", now);
        QuestionHistory q2 = makeQuestionHistory(2L, "q2", "a2", now.plusDays(1));
        QuestionHistory q3 = makeQuestionHistory(3L, "q3", "a3", now.plusDays(2));
        List<QuestionHistory> histories = List.of(q1, q2, q3);

        // when
        List<HistoryMetaInfo> results = histories.stream()
                .map(QuestionHistory::toHistoryMetaInfo)
                .toList();

        // then
        assertThat(results).hasSize(3)
                .extracting("id", "question", "talkedTime")
                .containsExactlyInAnyOrder(
                        tuple(1L, "q1", "2024-08-23 00:00"),
                        tuple(2L, "q2", "2024-08-24 00:00"),
                        tuple(3L, "q3", "2024-08-25 00:00")
                );
    }

    private QuestionHistory makeQuestionHistory(long id, String question, String answer, LocalDateTime talkedTime) {
        return QuestionHistory.builder()
                .id(id)
                .question(question)
                .answer(answer)
                .talkedTime(talkedTime)
                .build();
    }
}