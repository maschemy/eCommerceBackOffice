package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
