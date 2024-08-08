package com.aiia.gpt_be;


import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Talk, Long> {
}
