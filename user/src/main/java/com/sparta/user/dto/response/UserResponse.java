package com.sparta.user.dto.response;

import com.sparta.user.entity.Role;

public record UserResponse(
        String username,
        String email,
        Role role
) {}
