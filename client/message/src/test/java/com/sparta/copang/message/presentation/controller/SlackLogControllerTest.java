package com.sparta.copang.message.presentation.controller;

import com.sparta.copang.message.application.dto.CreateSlackLogReq;
import com.sparta.copang.message.application.service.SlackLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class SlackLogControllerTest {

    @Mock
    private SlackLogService SlackLogService;

    @InjectMocks
    private SlackLogController slackLogController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(slackLogController).build();
    }

    @Test
    @DisplayName("슬랙 메시지 전송 기록 생성 테스트")
    public void createSlackMessageTest() throws Exception {
        // Given
        String content = "{\"recipientEmail\":\"mii2026@naver.com\",\"message\":\"test\"}";

        // When
        doNothing().when(SlackLogService).createSlackLog(any(CreateSlackLogReq.class));

        // Then
        mockMvc.perform(post("/api/slacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result.resultCode").value("200"))
                .andExpect(jsonPath("result.resultMessage").value("Success"));
    }

    @Test
    @DisplayName("슬랙 메시지 전송 싱패 테스트 - 불가능한 이메일 형식")
    public void createSlackMessageTest2() throws Exception {
        // Given
        String content = "{\"recipientEmail\":\"mii2026\",\"message\":\"test\"}";

        // Then
        mockMvc.perform(post("/api/slacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("슬랙 메시지 전송 싱패 테스트 - 불가능한 메시지")
    public void createSlackMessageTest3() throws Exception {
        // Given
        String content = "{\"recipientEmail\":\"mii2026@naver.com\",\"message\":\" \"}";
        // Then
        mockMvc.perform(post("/api/slacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}