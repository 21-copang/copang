package com.sparta.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u.role FROM p_users u WHERE u.id = :id")
    Role findRoleById(UUID id);

    // 이메일로 사용자 검색
    Optional<User> findByEmail(String email);

}
