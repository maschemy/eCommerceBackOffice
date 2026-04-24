package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

public class LoginFailException extends ServiceException{

    public LoginFailException(String message)
    {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
