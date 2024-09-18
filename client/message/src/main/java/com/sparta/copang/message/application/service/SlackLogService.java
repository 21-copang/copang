package com.sparta.copang.message.application.service;

import com.sparta.copang.message.application.dto.CreateSlackLogReq;
import com.sparta.copang.message.domain.entity.SlackLog;
import com.sparta.copang.message.domain.repository.SlackLogRepository;
import com.sparta.copang.message.infrastructure.restTemplate.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackLogService {

    private final SlackLogRepository slackLogRepository;

    private final SlackService slackService;

    public void createSlackLog(CreateSlackLogReq requestDto) {
        slackService.sendSlackMessage(requestDto);
        SlackLog slackLog = SlackLog.createSlackLog(requestDto);
        slackLogRepository.save(slackLog);
    }
}
