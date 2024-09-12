package com.sparta.copang.message.infrastructure.repository;

import com.sparta.copang.message.domain.entity.SlackLog;
import com.sparta.copang.message.domain.repository.SlackLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackLogJPARepository extends JpaRepository<SlackLog, UUID>, SlackLogRepository {
}
