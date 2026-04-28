package com.ecommercebackoffice.auth.jwt.exception;

import com.ecommercebackoffice.common.exception.LoginFailException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //401 예외처리
    private final HandlerExceptionResolver resolver; // HandlerExceptionResolver에게 예외 위임

    public JwtAuthenticationEntryPoint(
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }


    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        resolver.resolveException(
                request,
                response,
                null,
                new LoginFailException("로그인이 필요합니다")
        );
    }
}