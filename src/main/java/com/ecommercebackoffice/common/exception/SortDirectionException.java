package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

// 정렬 방식 예외 처리
public class SortDirectionException extends ServiceException{
    public SortDirectionException(String message){
        super(HttpStatus.BAD_REQUEST,message);
    }
}