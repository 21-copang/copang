package com.sparta.copang.message.domain.entity;

import com.sparta.copang.message.application.dto.CreateSlackLogReq;
import com.sparta.copang.message.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_slack_logs")
public class SlackLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID slackId;
    private String recipientEmail;
    private String message;

    public static SlackLog createSlackLog(CreateSlackLogReq request) {
        return SlackLog.builder()
                .recipientEmail(request.recipientEmail())
                .message(request.message())
                .build();
    }
}
