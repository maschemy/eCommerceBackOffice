package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
}
