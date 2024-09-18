package com.sparta.gateway.filter;

import com.sparta.gateway.client.AuthClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class); // Logger 추가

    private final AuthClient authClient;

    @Value("${jwt.secret}")
    private String secretKey;

    public JwtAuthenticationFilter(@Lazy AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 화이트 리스트 설정
        String path = exchange.getRequest().getURI().getPath(); // 요청 URI 경로를 가져옴
        logger.info("요청 경로: {}", path); // 요청 경로 로그 추가
        if (path.equals("/auth/login") || path.equals("/auth/signup")) {
            logger.info("로그인 또는 회원가입 경로이므로 JWT 검증을 생략합니다: {}", path); // 로그인/회원가입 요청은 JWT 검증 생략
            return chain.filter(exchange);
        }

        // 토큰 검증 실패 시 로직
        String token = extractToken(exchange);
        if (token == null) {
            logger.warn("요청에서 토큰을 찾을 수 없습니다."); // 토큰이 없을 경우 경고 로그
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // 응답 상태 코드를 401로 설정
            return onError(exchange, "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        if (!validateToken(token, exchange)) {
            logger.warn("토큰 검증 실패: {}", token); // 토큰 검증 실패 로그
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return onError(exchange, "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        logger.info("토큰 검증 성공, 요청을 계속 처리합니다."); // 토큰 검증 성공 로그
        return chain.filter(exchange);
    }

    // 토큰 검증 및 Claims 추출
    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            logger.info("토큰을 검증 중입니다: {}", token); // 토큰 검증 로그

            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            logger.info("클레임 추출 완료: {}", claims); // 클레임 로그

            String userId = claims.get("user_id").toString();
            String userName = claims.get("user_name").toString();
            logger.info("사용자 ID: {}, 사용자 이름: {}", userId, userName); // 사용자 정보 로그

            exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Name", userName)
                    .header("X-User-Role", authClient.getPermission(UUID.fromString(userId)))
                    .build();
            return true;
        } catch (Exception e) {
            logger.error("토큰 검증 중 오류 발생: {}", e.getMessage()); // 토큰 검증 실패 시 오류 로그
            return false;
        }
    }

    // 에러 메시지, 상태 코드 반환
    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus status) {
        logger.error("오류 발생: {}, 상태 코드: {}", errorMsg, status); // 에러 발생 시 로그
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String body = "{\"error\": \"" + errorMsg + "\"}";

        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(body.getBytes())));
    }

    // 토큰 substring
    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            logger.info("Authorization 헤더에서 토큰을 추출합니다."); // 토큰 추출 로그
            return authHeader.substring(7);
        }
        logger.warn("Authorization 헤더가 없거나 Bearer로 시작하지 않습니다."); // 토큰이 없을 경우 경고 로그
        return null;
    }
}
