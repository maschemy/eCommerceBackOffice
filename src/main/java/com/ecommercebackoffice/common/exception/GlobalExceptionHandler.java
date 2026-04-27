package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bean Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerNotValidException(MethodArgumentNotValidException e) {
        String exceptionMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(exception -> exception.getDefaultMessage())
                .orElse("잘못된 입력값입니다.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    // 예외 발생 처리
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // 로그인 없이 다른행동할경우
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<String> handleServletRequestBindingException(ServletRequestBindingException e)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("로그인이 필요합니다.");
    }

    //커스텀 에러처리
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleIllegalStateException(ServiceException e)
    {
        return ResponseEntity
                .status(e.getStatus()) //HTTP 메소드
                .body(e.getMessage()); //메세지
    }

    // role 값에 이상한값이 들어갔을때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ) {
        // AdminRole enum 변환 실패인지 확인
        if (e.getMessage().contains("AdminRole")) {
            return ResponseEntity
                    .badRequest()
                    .body("관리자 역할은 SUPER_ADMIN, OPERATE_ADMIN, CS_ADMIN 중 하나여야 합니다.");
        }
        if (e.getMessage().contains("AdminStatus")) {
            return ResponseEntity
                    .badRequest()
                    .body("관리자 상태는 PENDING, ACTIVE, REJECTED, SUSPENDED, INACTIVE 중 하나여야 합니다.");
        }
        // 그 외의 오류
        return ResponseEntity
                .badRequest()
                .body("잘못된 요청입니다.");
    }
}
