package com.sparta.copang.order.infrastructure.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template){
        template.header("X-User-Id", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        template.header("X-User-Role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString().substring(5));
    }

}
