package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

public class UsedEmailException extends ServiceException{

    public UsedEmailException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
