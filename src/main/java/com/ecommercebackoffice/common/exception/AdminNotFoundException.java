package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends ServiceException{

    public AdminNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
