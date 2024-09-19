package com.sparta.user.service;

import com.sparta.user.dto.response.UserResponse;
import com.sparta.user.entity.User;
import com.sparta.user.entity.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 이메일로 사용자 검색
    public UserResponse searchUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 존재하지 않습니다."));

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

}
