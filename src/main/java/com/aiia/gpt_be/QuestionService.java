package com.aiia.gpt_be;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private static final String URI = "http://127.0.0.1:5000/request";
    private final RestClient restClient = RestClient.create();
    private final TalkRepository talkRepository;

    @Transactional
    public QuestionReply answer(QuestionServiceRequest request, LocalDateTime now) {
        Talk talk = Talk.of(request.getQuestion(), getAnswerFromGPT(request), now);
        Talk savedTalk = talkRepository.save(talk);
        return savedTalk.toReply();
    }

    private String getAnswerFromGPT(QuestionServiceRequest request) {

        return restClient.post()
                .uri(URI)
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
