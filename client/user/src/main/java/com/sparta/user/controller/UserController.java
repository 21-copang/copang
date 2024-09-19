package com.sparta.user.controller;

import com.sparta.user.common.ApiResponse;
import com.sparta.user.dto.request.UserRoleUpdateRequest;
import com.sparta.user.dto.request.UserUpdateRequest;
import com.sparta.user.entity.Role;
import com.sparta.user.service.UserSearchService;
import com.sparta.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;

    public UserController(UserService userService, UserSearchService userSearchService) {
        this.userService = userService;
        this.userSearchService = userSearchService;
    }

    // 사용자 정보 수정
    @PatchMapping("")
    public ApiResponse<?> updateUser(@RequestHeader(value = "X-User-Id", required = true) String userId, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
        return ApiResponse.success("OK", userId, "사용자 정보 수정 성공");
    }

    // 사용자 권한 수정 (MASTER 전용)
    @PatchMapping("/role/{targetUserId}")
    public ApiResponse<?> updateUserRole(@RequestHeader(value = "X-User-Id", required = true) UUID userId,
                                         @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                         @PathVariable UUID targetUserId, @RequestBody UserRoleUpdateRequest userRoleUpdateRequest) {

        if (!userRole.equals(Role.MASTER.name())) {
            throw new IllegalArgumentException("사용자 역할을 수정할 권한이 없습니다.");
        }
        userService.updateUserRole(targetUserId, userRoleUpdateRequest);
        return ApiResponse.success("OK", userId, "사용자 역할 수정 성공");
    }

    // 특정 사용자 정보 조회
    @GetMapping("/{targetUserId}")
    public ApiResponse<?> getUser(@RequestHeader(value = "X-User-Id", required = true) UUID userId,
                                  @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                  @PathVariable UUID targetUserId) {

        if (!userId.equals(targetUserId) && !userRole.equals(Role.MASTER.name())) {
            throw new IllegalArgumentException("해당 사용자에 접근할 권한이 없습니다.");
        }
        return ApiResponse.success("OK", userService.getUser(targetUserId), "사용자 조회 성공");
    }

    // 모든 사용자 정보 조회 (MASTER 전용)
    @GetMapping("/all")
    public ApiResponse<?> getAllUsers(@RequestHeader(value = "X-User-Id", required = true) UUID userId,
                                      @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                      Pageable pageable) {

        if (!userRole.equals(Role.MASTER.name())) {
            throw new IllegalArgumentException("모든 사용자에 접근할 권한이 없습니다.");
        }
        return ApiResponse.success("OK", userService.getAllUsers(userId, pageable), "사용자 목록 조회 성공");
    }

    // 사용자 삭제
    @DeleteMapping("")
    public ApiResponse<?> deleteUser(@RequestHeader(value = "X-User-Id", required = true) UUID userId) {
        userService.deleteUser(userId);
        return ApiResponse.success("OK", userId, "사용자 삭제 성공");
    }

    // 사용자 검색
    @GetMapping("/search")
    public ApiResponse<?> searchUserByEmail(@RequestParam(value = "email", required = true) String email) {
        return ApiResponse.success("OK", userSearchService.searchUserByEmail(email), "사용자 검색 성공");
    }

}
