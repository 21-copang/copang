package com.sparta.user.service;

import com.sparta.user.dto.request.UserRoleUpdateRequest;
import com.sparta.user.dto.request.UserUpdateRequest;
import com.sparta.user.dto.response.UserResponse;
import com.sparta.user.dto.response.UserUpdateResponse;
import com.sparta.user.dto.response.UserRoleUpdateResponse;
import com.sparta.user.entity.Role;
import com.sparta.user.entity.User;
import com.sparta.user.entity.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_ROLE = "userRole";

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 정보 수정
    public UserUpdateResponse updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (userUpdateRequest.username() != null) {
            user.setUsername(userUpdateRequest.username());
        }
        if (userUpdateRequest.password() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateRequest.password()));
        }
        userRepository.save(user);

        return new UserUpdateResponse("사용자 정보가 성공적으로 수정되었습니다.", userId);
    }

    // 사용자 권한 수정
    @CacheEvict(cacheNames = USER_ROLE, key = "#targetUserId")
    public UserRoleUpdateResponse updateUserRole(UUID targetUserId, UserRoleUpdateRequest userRoleUpdateRequest) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Role newRole = userRoleUpdateRequest.role();
        user.setRole(newRole);
        userRepository.save(user);

        return new UserRoleUpdateResponse("사용자 역할이 성공적으로 수정되었습니다.", targetUserId.toString());
    }

    // 특정 사용자 정보 조회
    public UserResponse getUser(UUID targetUserId) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new UserResponse(user.getUsername(), user.getEmail(), user.getRole());
    }

    // 모든 사용자 조회
    public Page<UserResponse> getAllUsers(UUID userId, Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(user -> new UserResponse(user.getUsername(), user.getEmail(), user.getRole()));
    }

    // 사용자 삭제
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        userRepository.delete(user);
    }

}
