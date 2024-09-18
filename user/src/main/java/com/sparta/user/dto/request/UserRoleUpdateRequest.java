package com.sparta.user.dto.request;

import com.sparta.user.entity.Role;
import jakarta.validation.constraints.NotNull;

public record UserRoleUpdateRequest(
//        @NotNull(message = "역할은 필수 항목입니다.")
        Role role
) {}
