package com.ecommercebackoffice.config;

import com.ecommercebackoffice.auth.jwt.JwtAuthenticationFilter;
import com.ecommercebackoffice.auth.jwt.exception.JwtAccessDeniedHandler;
import com.ecommercebackoffice.auth.jwt.exception.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain sercuritySecurityFilterChain(HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler)throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) // csrf = 세션공격방지 : 비활성화
                .formLogin(AbstractHttpConfigurer::disable)// 기본로그인페이지 : 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)// 기본 ID/PW 인증 : 비활성화
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401
                        .accessDeniedHandler(jwtAccessDeniedHandler)           // 403
                )
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 가능
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admins").permitAll()
                        // 관리자 관리: 슈퍼 관리자만
                        .requestMatchers("/admins/**").hasRole("SUPER_ADMIN")
                        // 고객 관리
                        .requestMatchers("/customers/**").hasRole("SUPER_ADMIN")
                        // 상품 관리
                        .requestMatchers("/api/products/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN")
                        // 주문 관리
                        .requestMatchers("/orders/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN", "CS_ADMIN")
                        // 리뷰 관리
                        .requestMatchers("/reviews/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN")
                        // 나머지는 로그인 필요
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
