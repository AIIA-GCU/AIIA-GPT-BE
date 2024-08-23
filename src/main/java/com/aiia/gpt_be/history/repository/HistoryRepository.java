package com.aiia.gpt_be.history.repository;

import com.aiia.gpt_be.question.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<QuestionHistory, Long>, HistoryRepositoryCustom {
}
