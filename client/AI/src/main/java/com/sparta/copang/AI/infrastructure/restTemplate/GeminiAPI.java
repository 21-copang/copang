package com.sparta.copang.AI.infrastructure.restTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiAPI {

    @Value("${gemini.api-key}")
    private String api_Key;

    private final RestTemplate restTemplate;

    public String sendRequest(String prompt) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com")
                .path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", api_Key)
                .build()
                .toUri();

        log.info("uri: {}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // 요청 바디 생성
        Map<String, Object> parts = new HashMap<>();
        parts.put("text", prompt + "답변을 최대한 간결하게 50자 이하로");

        List<Map<String, Object>> partsList = new ArrayList<>();
        partsList.add(parts);

        Map<String, Object> contents = new HashMap<>();
        contents.put("parts", partsList);

        List<Map<String, Object>> contentsList = new ArrayList<>();
        contentsList.add(contents);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", contentsList);

        // HTTP 요청 엔티티 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // POST 요청을 보내고 응답을 받아옵니다.
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                String.class
        );


        log.info("statusCode = {}", responseEntity.getStatusCode());

        // 응답 바디를 반환합니다.
        return responseEntity.getBody();

    }


}
