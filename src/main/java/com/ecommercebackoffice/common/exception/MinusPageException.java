package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

// 페이지 정보 음수 입력 시 예외 처리
public class MinusPageException extends ServiceException{
    public MinusPageException(String message){
        super(HttpStatus.BAD_REQUEST,message);
    }
}
