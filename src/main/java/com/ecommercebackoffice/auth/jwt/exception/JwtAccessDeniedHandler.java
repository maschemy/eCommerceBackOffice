package com.ecommercebackoffice.auth.jwt.exception;

import com.ecommercebackoffice.common.exception.AdminPermissionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAccessDeniedHandler
        implements AccessDeniedHandler {

    //403 예외처리
    private final HandlerExceptionResolver resolver; // HandlerExceptionResolver에게 예외 위임

    public JwtAccessDeniedHandler(
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) {

        resolver.resolveException(
                request,
                response,
                null,
                new AdminPermissionException("접근 권한이 없습니다.")
        );
    }
}
