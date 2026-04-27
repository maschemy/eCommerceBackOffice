package com.ecommercebackoffice.admin.enums;

import com.ecommercebackoffice.common.exception.AdminPermissionException;

public enum AdminStatus {
    PENDING,    // 승인대기
    ACTIVE,     // 활성
    REJECTED,   // 거부
    SUSPENDED,  // 정지
    INACTIVE; // 비활성

    public void validateLogin() {
        if (this != ACTIVE) {
            throw new AdminPermissionException(getMessage());
        }
    }

    private String getMessage() {
        return switch (this) {
            case INACTIVE -> "비활성화된 계정입니다";
            case SUSPENDED -> "정지된 계정입니다";
            case PENDING -> "승인 대기중인 계정입니다";
            case REJECTED -> "거부된 계정입니다";
            default -> "로그인할 수 없는 상태입니다";
        };
    }
}



