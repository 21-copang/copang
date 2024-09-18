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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class); // Logger 추가

    private static final String USER_ROLE = "userRole";
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${jwt.expiration}")
    Long accessExpiration;

    public AuthService(AuthRepository authRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(UserSignupRequest request) {
        logger.info("회원가입 요청: 이메일 = {}", request.getEmail()); // 회원가입 로그

        Optional<User> checkUser = authRepository.findByEmail(request.getEmail());
        if (checkUser.isPresent()) {
            logger.warn("이미 존재하는 사용자: {}", request.getEmail()); // 사용자 중복 경고 로그
            throw new RuntimeException(ErrorCode.EXIST_USER.name());
        }

        if (request.getRole().equals(Role.MASTER)) {
            request.setRole(Role.MASTER);
            logger.info("사용자 역할이 MASTER로 설정되었습니다."); // 역할 설정 로그
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = new User(request.getEmail(), request.getUsername(), request.getPassword(), request.getRole());
        User savedUser = authRepository.save(user);
        logger.info("회원가입 성공: 사용자 ID = {}", savedUser.getId()); // 회원가입 성공 로그

        return savedUser;
    }

    public String login(UserLoginRequest request) {
        logger.info("로그인 요청: 이메일 = {}", request.getEmail()); // 로그인 요청 로그

        User user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("로그인 실패 - 사용자 정보가 없습니다: {}", request.getEmail()); // 로그인 실패 로그
                    return new RuntimeException(ErrorCode.INVALID_CREDENTIALS.name());
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.error("로그인 실패 - 비밀번호 불일치: 이메일 = {}", request.getEmail()); // 비밀번호 불일치 로그
            throw new RuntimeException(ErrorCode.INVALID_CREDENTIALS.name());
        }

        String accessToken = createAccessToken(user.getId().toString(), user.getUsername().toString());
        logger.info("로그인 성공: 사용자 ID = {}, 액세스 토큰 생성 완료", user.getId()); // 로그인 성공 및 토큰 생성 로그

        return accessToken;
    }

    public String createAccessToken(String userId, String username) {
        logger.info("액세스 토큰 생성: 사용자 ID = {}, 사용자 이름 = {}", userId, username); // 액세스 토큰 생성 로그

        String token = Jwts.builder()
                .claim("user_id", userId)
                .claim("user_name", username)
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        logger.info("액세스 토큰 생성 완료: 사용자 ID = {}", userId); // 토큰 생성 완료 로그
        return token;
    }

    @Cacheable(cacheNames = USER_ROLE, key = "#id")
    public Role getPermission(UUID id) {
        logger.info("권한 조회 요청: 사용자 ID = {}", id); // 권한 조회 로그
        Role role = userRepository.findRoleById(id);
        logger.info("권한 조회 완료: 사용자 ID = {}, 권한 = {}", id, role); // 권한 조회 완료 로그
        return role;
    }
}
