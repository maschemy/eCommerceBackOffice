package com.ecommercebackoffice.common.exception;


import org.springframework.http.HttpStatus;

//고객이 존재하지 않을 때 예외처리
public class NotFoundException extends ServiceException{
    public NotFoundException(String message){
        super(HttpStatus.NOT_FOUND,message);
    }
}
