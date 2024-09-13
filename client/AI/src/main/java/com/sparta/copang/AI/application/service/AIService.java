package com.sparta.copang.AI.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.copang.AI.domain.model.AI;
import com.sparta.copang.AI.domain.repository.AIRepository;
import com.sparta.copang.AI.infrastructure.restTemplate.GeminiAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {



    private final AIRepository aiRepository;
    private final GeminiAPI geminiService;



    public String requestPrompt(String prompt) {
        String response = extractTextFromResponse(geminiService.sendRequest(prompt));

        AI ai = AI.builder().
                ai_id(UUID.randomUUID())
                .request(prompt)
                .response(response)
                .build();

        aiRepository.save(ai);
        return response;
    }

    // JSON 응답에서 text 값을 추출하는 메서드
    private String extractTextFromResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode textNode = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text");
            return textNode.asText();
        } catch (Exception e) {
            log.error("Error parsing response JSON", e);
            return null;
        }
    }

}
