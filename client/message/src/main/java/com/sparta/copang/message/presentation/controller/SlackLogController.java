package com.sparta.copang.message.presentation.controller;

import com.sparta.copang.message.application.dto.CreateSlackLogReq;
import com.sparta.copang.message.application.service.SlackLogService;
import com.sparta.copang.message.presentation.controller.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slacks")
public class SlackLogController {

    private final SlackLogService slackService;

    @PostMapping
    public ResponseEntity<CommonResponse<String>> createSlackLogs(@RequestBody @Valid CreateSlackLogReq requestDto){
        slackService.createSlackLog(requestDto);
        return ResponseEntity
                .ok(CommonResponse.OK(null));
    }

}
