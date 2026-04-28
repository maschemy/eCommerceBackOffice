package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

// 정렬 기준 컬럼에 없는 값 입력 시 예외 처리
public class SortByException extends ServiceException{
    public SortByException(String message){
        super(HttpStatus.BAD_REQUEST,message);
    }
}
