package com.ecommercebackoffice.config;

import com.ecommercebackoffice.auth.jwt.JwtAuthenticationFilter;
import com.ecommercebackoffice.auth.jwt.exception.JwtAccessDeniedHandler;
import com.ecommercebackoffice.auth.jwt.exception.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
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
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/auth/login").permitAll() //로그인 접근허용
                                .requestMatchers(HttpMethod.POST,"/admins").permitAll()//가입 접근허용
                                .requestMatchers("/admins/**").hasRole("SUPER_ADMIN")//슈퍼관리자만 접근가능
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
