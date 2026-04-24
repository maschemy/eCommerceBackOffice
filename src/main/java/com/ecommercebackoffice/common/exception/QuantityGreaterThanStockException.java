package com.ecommercebackoffice.common.exception;

import org.springframework.http.HttpStatus;

public class QuantityGreaterThanStockException extends ServiceException{

    public QuantityGreaterThanStockException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
