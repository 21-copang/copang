package com.sparta.user.auth.service;

import com.sparta.user.auth.dto.UserLoginRequest;
import com.sparta.user.auth.dto.UserSignupRequest;
import com.sparta.user.entity.AuthRepository;
import com.sparta.user.common.ErrorCode;
import com.sparta.user.entity.Role;
import com.sparta.user.entity.User;
import com.sparta.user.entity.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private static final String USER_ROLE = "userRole";
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${jwt.expiration}")
    Long accessExpiration;

    @Value("${ADMIN_TOKEN}")
    String ADMIN_TOKEN;

    public AuthService(AuthRepository authRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(UserSignupRequest request) {
        Optional<User> checkUser = authRepository.findByEmail(request.getEmail());
        if (checkUser.isPresent()) {
            throw new RuntimeException(ErrorCode.EXIST_USER.name());
        }
        if ((request.getRole()).equals(Role.MASTER)) {
            if (!ADMIN_TOKEN.equals(request.getAdminToken())) {
                throw new RuntimeException(ErrorCode.INVALID_ADMIN_TOKEN.name());
            }
            request.setRole(Role.MASTER);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = new User(request.getEmail(), request.getUsername(), request.getPassword(), request.getRole());
        return authRepository.save(user);
    }

    public String login(UserLoginRequest request) {
        User user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(ErrorCode.INVALID_CREDENTIALS.name()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException(ErrorCode.INVALID_CREDENTIALS.name());
        }
        return createAccessToken(user.getId().toString(), user.getUsername().toString());
    }

    public String createAccessToken(String userId, String username) {
        return Jwts.builder()
                .claim("user_id", userId)
                .claim("username", username)
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Cacheable(cacheNames = USER_ROLE, key = "#id")
    public Role getPermission(UUID id) {
        return userRepository.findRoleById(id);
    }

}
