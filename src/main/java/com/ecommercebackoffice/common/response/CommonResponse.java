package com.ecommercebackoffice.common.response;

import lombok.Getter;

@Getter
public class CommonResponse {
    private final String message;
    private final int statusCode;
    private final Object data;

    public CommonResponse(String message, int statusCode, Object data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }
}
