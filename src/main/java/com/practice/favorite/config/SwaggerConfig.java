package com.practice.favorite.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .name("Authorization").in(SecurityScheme.In.HEADER)
                                .scheme("Bearer").bearerFormat("JWT")))
                .security(Arrays.asList(new SecurityRequirement().addList("bearerAuth")))
                .info(new Info().title("Favorite-folder-API")
                        .version("v0.0.1")
                );
    }

}

