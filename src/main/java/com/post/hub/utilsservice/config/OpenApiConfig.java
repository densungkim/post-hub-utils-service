package com.post.hub.utilsservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "POST_HUB REST API",
                version = "1.0",
                description = """
                        UTILS-service REST API

                        To use this API, you must include a valid JWT token in the Authorization header.
                        JWT tokens are issued by the IAM Service.

                        Please obtain a token from the IAM Service first, then use it to access the Utils Service endpoints.
                        """
        ),
        security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)}
)
@SecurityScheme(
        name = HttpHeaders.AUTHORIZATION,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi utilsApi() {
        SpringDocUtils.getConfig().replaceWithClass(LocalDateTime.class, Long.class);
        SpringDocUtils.getConfig().replaceWithClass(LocalDate.class, Long.class);
        SpringDocUtils.getConfig().replaceWithClass(Date.class, Long.class);

        SpringDocUtils.getConfig().addResponseTypeToIgnore(GrantedAuthority.class);

        return GroupedOpenApi.builder()
                .group("utils-service")
                .packagesToScan("com.post.hub.utilsservice")
                .build();
    }

}
