package com.sparta.user.dto.response;

import com.sparta.user.entity.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        Role role
) {}
