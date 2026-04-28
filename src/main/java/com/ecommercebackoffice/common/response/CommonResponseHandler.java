package com.ecommercebackoffice.common.response;

import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CommonResponseHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body,
                                            MethodParameter returnType,
                                            MediaType selectedContentType,
                                            Class selectedConverterType,
                                            ServerHttpRequest request,
                                            ServerHttpResponse response) {
        // 상태 코드 초기화
        int statusCode = 0;

        // 상태 코드 가져오기
        // 1. "혹시 너, 서블릿 환경용 통역기(ServletServerHttpResponse) 맞니?" 라고 물어봅니다.
        if (response instanceof ServletServerHttpResponse) {

            // 2. 맞다면 껍데기를 벗기고, 그 안에 숨어있던 '진짜 알맹이(HttpServletResponse)'를 꺼냅니다.
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

            // 3. 진짜 알맹이한테는 상태 코드(200, 404 등)를 물어볼 수 있습니다!
            statusCode = servletResponse.getStatus();
        }

        // 상태코드 400 이상 예외 발생 시 공통 응답 없이 반환
        if (statusCode >= 400) {
            return body;
        }

        // 공통 응답 반환
        return new CommonResponse("성공", statusCode, body);
    }
}
