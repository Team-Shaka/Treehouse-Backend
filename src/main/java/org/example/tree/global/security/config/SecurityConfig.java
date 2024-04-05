package org.example.tree.global.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.global.security.filter.JwtAuthFilter;
import org.example.tree.global.security.handler.JwtAccessDeniedHandler;
import org.example.tree.global.security.handler.JwtAuthenticationEntryPoint;
import org.example.tree.global.security.handler.JwtAuthenticationExceptionHandler;
import org.example.tree.global.security.provider.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@RequiredArgsConstructor
//@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler = new JwtAccessDeniedHandler();

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler =
            new JwtAuthenticationExceptionHandler();

    /**
     * 특정 경로에 대한 보안 설정을 무시하도록 설정
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring()
                        .requestMatchers(
                                "/health",
                                "/schedule",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/favicon.io",
                                "/swagger-ui/**",
                                "/docs/**");
    }

    @Bean
    public SecurityFilterChain JwtFilterChain(HttpSecurity http) throws Exception {
        return http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // 비활성화
                .sessionManagement(
                        manage ->
                                manage.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS)) // Session 사용 안함
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> {
//                            authorize.requestMatchers("/swagger-ui/**").permitAll();
                            authorize.requestMatchers("/users/**").permitAll();
                            authorize.anyRequest().authenticated();
                })
                .exceptionHandling(
                        exceptionHandling ->
                                exceptionHandling
                                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilterBefore(
                        new JwtAuthFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationExceptionHandler, JwtAuthFilter.class)
                .build();
    }

    public CorsConfigurationSource corsConfiguration() {
        return request -> {
            org.springframework.web.cors.CorsConfiguration config =
                    new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
            config.setAllowedMethods(Collections.singletonList("*")); // 모든 메소드 허용
            config.setAllowedOriginPatterns(Collections.singletonList("*")); // 모든 Origin 허용
            config.setAllowCredentials(true);   // 인증정보 허용
            return config;
        };
    }
}
