package com.sparta.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

}
