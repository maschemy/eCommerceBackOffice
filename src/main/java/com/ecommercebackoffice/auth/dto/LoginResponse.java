package com.ecommercebackoffice.auth.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final Long id;
    private final String email;
    private final AdminRole role;

    public LoginResponse(Long id, String email, AdminRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
