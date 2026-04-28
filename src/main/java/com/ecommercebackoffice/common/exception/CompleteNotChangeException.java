package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

// 배송완료된 주문은 변경 불가 예외 처리
public class CompleteNotChangeException  extends ServiceException{
    public CompleteNotChangeException (String message){
        super(HttpStatus.CONFLICT,message);
    }
}