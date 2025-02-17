package com.sparta.copang.AI.presentation.controller;

import com.sparta.copang.AI.application.service.AIService;
import com.sparta.copang.AI.infrastructure.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    @PostMapping
    public ResponseEntity<CommonResponse<?>> requestPrompt(@RequestBody String prompt) {

        return ResponseEntity.ok(CommonResponse.OK(aiService.requestPrompt(prompt)));
    }
}
