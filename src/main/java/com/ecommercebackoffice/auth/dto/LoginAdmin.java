package com.ecommercebackoffice.auth.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;

public record LoginAdmin(Long adminId, String email, AdminRole role) {


//    public  void allowSuperAdmin() {
//        if (this.role() != AdminRole.SUPER_ADMIN) {
//            throw new AdminPermissionException("슈퍼 관리자만 접근할 수 있습니다.");
//        }
//    } JWT 인가에서 처리
}
