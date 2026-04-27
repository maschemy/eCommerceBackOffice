package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

public class PasswordIncorrectException extends ServiceException{

    public PasswordIncorrectException(String message)
    {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

