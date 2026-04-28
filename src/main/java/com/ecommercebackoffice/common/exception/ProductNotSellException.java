package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

// 상품 판매중 아닐 경우 예외 처리
public class ProductNotSellException extends ServiceException{
    public ProductNotSellException(String message){
        super(HttpStatus.CONFLICT,message);
    }
}
