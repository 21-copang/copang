package com.sparta.copang.order.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Components components = new Components();

        components.addSecuritySchemes("X-User-Id", new SecurityScheme()
                .name("X-User-Id")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)

        );

        components.addSecuritySchemes("X-User-Name", new SecurityScheme()
                .name("X-User-Name")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)

        );

        components.addSecuritySchemes("X-User-Role", new SecurityScheme()
                .name("X-User-Role")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
        );

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("X-User-Id")
                .addList("X-User-Name")
                .addList("X-User-Role");

        return new OpenAPI().components(components)
                .security(Arrays.asList(securityRequirement));
    }

}