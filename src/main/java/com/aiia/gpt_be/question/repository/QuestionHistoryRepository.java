package com.aiia.gpt_be.question.repository;


import com.aiia.gpt_be.question.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory, Long> {
}
