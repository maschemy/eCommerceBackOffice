package com.ecommercebackoffice.auth.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.common.exception.AdminPermissionException;

public record LoginAdmin(Long adminId, String email, AdminRole role) {

    public  void allowSuperAdmin() {
        if (this.role() != AdminRole.SUPER_ADMIN) {
            throw new AdminPermissionException("슈퍼 관리자만 접근할 수 있습니다.");
        }
    }
}
