package com.sparta.copang.message.infrastructure.restTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.copang.message.application.dto.CreateSlackLogReq;
import com.sparta.copang.message.common.exception.ApplicationException;
import com.sparta.copang.message.presentation.controller.response.status.SlackStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SlackService {

    @Value("${slackBotToken}")
    private String slackToken;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    public void sendSlackMessage(CreateSlackLogReq requestDto) {
        String url = "https://slack.com/api/chat.postMessage";
        HttpHeaders headers = buildSlackHeaders();
        String slackUserId = getSlackUserIdByEmail(requestDto.recipientEmail());

        JsonNode messagePayload = createMessagePayload(slackUserId, requestDto.message());
        HttpEntity<String> request = new HttpEntity<>(messagePayload.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            if (objectMapper.readTree(response.getBody()).path("ok").asText().equals("false")) {
                throw new ApplicationException(SlackStatusCode.SEND_SLACK_MESSAGE_ERROR);
            }
        } catch (RestClientException | JsonProcessingException e) {
            throw new ApplicationException(SlackStatusCode.SEND_SLACK_MESSAGE_ERROR);
        }
    }

    public String getSlackUserIdByEmail(String email) {
        String url = "https://slack.com/api/users.lookupByEmail?email=" + email;
        HttpHeaders headers = buildSlackHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            if (objectMapper.readTree(response.getBody()).path("ok").asText().equals("false")) {
                throw new ApplicationException(SlackStatusCode.SEND_SLACK_MESSAGE_ERROR);
            }
            return objectMapper.readTree(response.getBody()).path("user").path("id").asText();
        } catch (RestClientException | JsonProcessingException e) {
            throw new ApplicationException(SlackStatusCode.GET_USERID_ERROR);
        }
    }

    private HttpHeaders buildSlackHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private JsonNode createMessagePayload(String channel, String message) {
        return objectMapper.createObjectNode()
                .put("channel", channel)
                .put("text", message);
    }
}
