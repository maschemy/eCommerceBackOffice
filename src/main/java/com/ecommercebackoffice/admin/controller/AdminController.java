package com.ecommercebackoffice.admin.controller;

import com.ecommercebackoffice.admin.dto.*;
import com.ecommercebackoffice.admin.service.AdminService;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    //────────────────────────────────────관리자생성────────────────────────────────────
    @PostMapping
    public ResponseEntity<CreateAdminResponseDto> createAdmin(@Valid @RequestBody CreateAdminRequestDto request) {
        CreateAdminResponseDto result = adminService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /*
    @PreAuthorize("hasRole('SUPER_ADMIN')") = 권한 인가
    @AuthenticationPrincipal LoginAdmin loginAdmin = 유저 인증
     */
    //────────────────────────────────────관리자전체조회────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<SearchAdminResponseDto>> getAllAdmin(
            @Valid SearchAdminRequestDto request) {
        Page<SearchAdminResponseDto> result = adminService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자상세조회────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{adminId}")
    public ResponseEntity<GetOneAdminResponseDto> getOneAdmin(
            @PathVariable Long adminId) {
        GetOneAdminResponseDto result = adminService.getOne(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자정보수정────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{adminId}")
    public ResponseEntity<UpdateAdminResponseDto> updateAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody UpdateAdminRequestDto request) {
        UpdateAdminResponseDto result = adminService.update(adminId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자역활변경────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{adminId}/role")
    public ResponseEntity<UpdateRoleResponseDto> updateRole(
            @AuthenticationPrincipal LoginAdmin loginAdmin,
            @PathVariable Long adminId,
            @RequestBody @Valid UpdateRoleRequestDto request
    ) {
        UpdateRoleResponseDto result = adminService.updateRole(loginAdmin,adminId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자 상태 변경────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{adminId}/status")
    public ResponseEntity<UpdateStatusResponseDto> updateStatus(
            @AuthenticationPrincipal LoginAdmin loginAdmin,
            @PathVariable Long adminId,
            @RequestBody @Valid UpdateStatusRequestDto request
    ) {
        UpdateStatusResponseDto result = adminService.updateStatus(loginAdmin,adminId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────관리자 삭제────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(
            @AuthenticationPrincipal LoginAdmin loginAdmin,
            @PathVariable Long adminId) {
        adminService.deleteAdmin(loginAdmin,adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //────────────────────────────────────관리자 승인────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{adminId}/approve")
    public ResponseEntity<Void> approveAdmin(
            @PathVariable Long adminId) {
        adminService.approveAdmin(adminId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //────────────────────────────────────관리자 거부────────────────────────────────────
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{adminId}/reject") //인증
    public ResponseEntity<Void> rejectAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody RejectAdminRequestDto request) {
        adminService.rejectAdmin(adminId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //────────────────────────────────────내프로필 조회────────────────────────────────────
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<GetMyInfoResponseDto> getInfo(
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        GetMyInfoResponseDto result = adminService.getMyInfo(loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────내프로필 수정────────────────────────────────────
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me")
    public ResponseEntity<UpdateMyInfoResponseDto> updateInfo(
            @AuthenticationPrincipal LoginAdmin loginAdmin,
            @Valid @RequestBody UpdateMyInfoRequestDto request
    ) {
        UpdateMyInfoResponseDto result = adminService.updateMyInfo(loginAdmin, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────비밀번호 수정────────────────────────────────────
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal LoginAdmin loginAdmin,
            @Valid @RequestBody ChangePasswordRequestDto request
    ) {
        adminService.changePassword(loginAdmin, request);
        return ResponseEntity.noContent().build();
    }
}
