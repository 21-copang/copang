package com.sparta.gateway.filter;

import com.sparta.gateway.client.AuthClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {


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
        if (path.equals("/auth/login") || path.equals("/auth/signup")) {
            return chain.filter(exchange);
        }

        // 토큰 검증 실패 시 로직
        String token = extractToken(exchange);
        if (token == null || !validateToken(token, exchange)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // 응답 상태 코드를 401로 설정
            return onError(exchange, "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    // 토큰 검증 및 Claims 추출
    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            String userId = claims.get("user_id").toString();
            String userName = claims.get("user_name").toString();
            exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Name", userName)
                    .header("X-User-Role", authClient.getPermission(Long.valueOf(userId)))
                    .build();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // 에러 메시지, 상태 코드 반환
    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus status) {
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
            return authHeader.substring(7);
        }
        return null;
    }
}
