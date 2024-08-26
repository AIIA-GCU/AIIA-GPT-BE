package com.aiia.gpt_be.history.repository;

import com.aiia.gpt_be.question.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<QuestionHistory, Long>, HistoryRepositoryCustom {
    Optional<QuestionHistory> findById(Long id);
}
