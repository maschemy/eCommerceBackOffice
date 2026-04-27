package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

public class AdminPermissionException extends ServiceException{

    public AdminPermissionException(String message)
        {
            super(HttpStatus.FORBIDDEN, message);
        }
}
