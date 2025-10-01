package com.post.hub.utilsservice.config;

import com.post.hub.utilsservice.security.filter.JwtTokenFilter;
import com.post.hub.utilsservice.security.model.IamServiceUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    private static final String[] PUBLIC_GET_URLS = new String[]{
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/**"
    };

    private static final String[] NOT_SECURED_GET_URLS = new String[]{"/action-logs/**", "/action-logs"};
    private static final String[] NOT_SECURED_PUT_URLS = new String[]{"/action-logs/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_GET_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET, NOT_SECURED_GET_URLS).hasAnyAuthority(allDashboardSecurityRoles())
                        .requestMatchers(HttpMethod.PUT, NOT_SECURED_PUT_URLS).hasAnyAuthority(allDashboardSecurityRoles())
                        .anyRequest().denyAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
                        })
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private String[] allDashboardSecurityRoles() {
        return Arrays.stream(IamServiceUserRole.values())
                .map(IamServiceUserRole::getRole)
                .toArray(String[]::new);
    }

}
