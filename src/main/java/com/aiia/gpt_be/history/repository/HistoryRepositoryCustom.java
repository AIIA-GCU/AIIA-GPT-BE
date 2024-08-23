package com.aiia.gpt_be.history.repository;

import com.aiia.gpt_be.question.QuestionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface HistoryRepositoryCustom {
    Page<QuestionHistory> getAllHistories(Pageable pageable);
}
