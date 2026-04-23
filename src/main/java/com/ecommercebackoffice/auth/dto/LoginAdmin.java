package com.ecommercebackoffice.auth.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;

public record LoginAdmin(Long adminId, String email, AdminRole role) {}
