package com.ecommercebackoffice.admin.controller;

import com.ecommercebackoffice.admin.dto.*;
import com.ecommercebackoffice.admin.service.AdminService;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final HttpSession httpSession;

    //────────────────────────────────────관리자생성────────────────────────────────────
    @PostMapping
    public ResponseEntity<CreateAdminResponseDto> createAdmin(@Valid @RequestBody CreateAdminRequestDto request) {
        CreateAdminResponseDto result = adminService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //────────────────────────────────────관리자전체조회────────────────────────────────────
    @GetMapping
    public ResponseEntity<Page<SearchAdminResponseDto>> getAllAdmin(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid @RequestBody SearchAdminRequestDto request) {
        loginAdmin.allowSuperAdmin();
        Page<SearchAdminResponseDto> result = adminService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자상세조회────────────────────────────────────
    @GetMapping("/{adminId}")
    public ResponseEntity<GetOneAdminResponseDto> getOneAdmin(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId) {
        loginAdmin.allowSuperAdmin();
        GetOneAdminResponseDto result = adminService.getOne(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자정보수정────────────────────────────────────
    @PatchMapping("/{adminId}")
    public ResponseEntity<UpdateAdminResponseDto> updateAdmin(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId,
            @Valid @RequestBody UpdateAdminRequestDto request) {
        loginAdmin.allowSuperAdmin();
        UpdateAdminResponseDto result = adminService.update(adminId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자역활변경────────────────────────────────────
    @PatchMapping("/{adminId}/role")
    public ResponseEntity<UpdateRoleResponseDto> updateRole(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId,
            @RequestBody @Valid UpdateRoleRequestDto request
    ) {
        loginAdmin.allowSuperAdmin();
        UpdateRoleResponseDto result = adminService.updateRole(adminId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자 상태 변경────────────────────────────────────
    @PatchMapping("/{adminId}/status")
    public ResponseEntity<UpdateStatusResponseDto> updateStatus(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId,
            @RequestBody @Valid UpdateStatusRequestDto request
    ) {
        loginAdmin.allowSuperAdmin();
        UpdateStatusResponseDto result = adminService.updateStatus(adminId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자 삭제────────────────────────────────────
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId) {
        loginAdmin.allowSuperAdmin();
        adminService.deleteAdmin(adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //────────────────────────────────────관리자 승인────────────────────────────────────
    @PatchMapping("/{adminId}/approve")
    public ResponseEntity<String> approveAdmin(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId) {
        loginAdmin.allowSuperAdmin();
        adminService.approveAdmin(adminId);
        return ResponseEntity.status(HttpStatus.OK).body("관리자의 의해 승인되었습니다");
    }

    //────────────────────────────────────관리자 거부────────────────────────────────────
    @PatchMapping("/{adminId}/reject")
    public ResponseEntity<String> rejectAdmin(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long adminId,
            @Valid @RequestBody RejectAdminRequestDto request) {
        loginAdmin.allowSuperAdmin();
        adminService.rejectAdmin(adminId, request);
        return ResponseEntity.status(HttpStatus.OK).body("관리자의 의해 거부되었습니다");
    }


    //────────────────────────────────────내프로필 조회────────────────────────────────────
    @GetMapping("/me")
    public ResponseEntity<GetMyInfoResponseDto> getInfo(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin
    ) {
        GetMyInfoResponseDto result = adminService.getMyInfo(loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────내프로필 수정────────────────────────────────────
    @PatchMapping("/me")
    public ResponseEntity<UpdateMyInfoResponseDto> updateInfo(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid @RequestBody UpdateMyInfoRequestDto request
    ) {
        UpdateMyInfoResponseDto result = adminService.updateMyInfo(loginAdmin, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────비밀번호 수정────────────────────────────────────
    @PatchMapping("/me/password")
    public ResponseEntity<String> changePassword(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid @RequestBody ChangePasswordRequestDto request,
            HttpSession session) {
        adminService.changePassword(loginAdmin, request);
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("비밀번호가 변경되었습니다. 다시 로그인해 주세요");
    }
}
