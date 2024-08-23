package com.aiia.gpt_be.question.service;

import com.aiia.gpt_be.question.QuestionHistory;
import com.aiia.gpt_be.question.repository.QuestionRepository;
import com.aiia.gpt_be.question.dto.QuestionReplyToUser;
import com.aiia.gpt_be.question.dto.QuestionServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    @Value("${flask.domain}")
    private String gptUri;
    private final RestClient restClient = RestClient.create();
    private final QuestionRepository questionRepository;

    public QuestionReplyToUser answer(QuestionServiceRequest request, LocalDateTime now) {
        QuestionHistory questionHistory = QuestionHistory.of(request.getQuestion(), getAnswerFromGPT(request), now);
        QuestionHistory savedQuestionHistory = questionRepository.save(questionHistory);
        return savedQuestionHistory.toReply();
    }

    private String getAnswerFromGPT(QuestionServiceRequest request) {
        return restClient.post()
                .uri(gptUri +":5000/request")
                .contentType(APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new IllegalArgumentException("잘못된 요청입니다!");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new IllegalArgumentException("파이썬 내부에서 에러 발생했습니다!");
                })
                .body(String.class);
    }
}
