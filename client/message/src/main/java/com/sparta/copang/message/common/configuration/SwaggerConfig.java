package com.sparta.copang.message.common.configuration;

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

        components.addSecuritySchemes("X-Username", new SecurityScheme()
                .name("X-Username")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)

        );

        components.addSecuritySchemes("X-Roles", new SecurityScheme()
                .name("X-Roles")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
        );

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("X-Username")
                .addList("X-Roles");

        return new OpenAPI().components(components)
                .security(Arrays.asList(securityRequirement));
    }

}
