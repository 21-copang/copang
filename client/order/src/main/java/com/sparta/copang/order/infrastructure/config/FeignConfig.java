package com.sparta.copang.order.infrastructure.config;

import com.sparta.copang.order.infrastructure.interceptor.AuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public AuthRequestInterceptor feignRequestInterceptor() {
        return new AuthRequestInterceptor();
    }
}
