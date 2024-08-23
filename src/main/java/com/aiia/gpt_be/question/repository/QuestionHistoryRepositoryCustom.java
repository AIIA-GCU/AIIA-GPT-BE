package com.aiia.gpt_be.question.repository;

import com.aiia.gpt_be.question.QuestionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface QuestionHistoryRepositoryCustom {
    Page<QuestionHistory> getAllHistories(Pageable pageable);
}
