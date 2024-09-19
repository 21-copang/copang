package com.sparta.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
//        @NotBlank(message = "사용자 이름은 필수 항목입니다.")
//        @Size(max = 100, message = "사용자 이름은 100자를 넘을 수 없습니다.")
        String username,
//
//        @NotBlank(message = "비밀번호는 필수 항목입니다.")
//        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

//        @Email(message = "올바른 이메일 형식을 입력해주세요.")
//        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email
) {}
