package com.ecommercebackoffice.admin.controller;

import com.ecommercebackoffice.admin.dto.*;
import com.ecommercebackoffice.admin.service.AdminService;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
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

    //────────────────────────────────────관리자생성────────────────────────────────────
    @PostMapping
    public ResponseEntity<CreateAdminResponseDto> createAdmin(@Valid @RequestBody CreateAdminRequestDto request)
    {
        CreateAdminResponseDto result = adminService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    //────────────────────────────────────관리자전체조회────────────────────────────────────
    @GetMapping
    public ResponseEntity<Page<SearchAdminResponseDto>> getAllAdmin(@Valid SearchAdminRequestDto request)
    {
        Page<SearchAdminResponseDto> result = adminService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────관리자상세조회────────────────────────────────────
    @GetMapping("/{adminId}")
    public ResponseEntity<GetOneAdminResponseDto> getOneAdmin(@PathVariable Long adminId)
    {
        GetOneAdminResponseDto result = adminService.getOne(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────관리자정보수정────────────────────────────────────
    @PatchMapping("/{adminId}")
    public ResponseEntity<UpdateAdminResponseDto> updateAdmin(@PathVariable Long adminId,
                                                              @Valid @RequestBody UpdateAdminRequestDto request)
    {
        UpdateAdminResponseDto result = adminService.update(adminId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────관리자역활변경────────────────────────────────────
    @PatchMapping("/{adminId}/role")
    public ResponseEntity<UpdateRoleResponseDto> updateRole(
            @PathVariable Long adminId,
            @RequestBody @Valid UpdateRoleRequestDto request
    ) {
        UpdateRoleResponseDto result = adminService.updateRole(adminId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────관리자 상태 변경────────────────────────────────────
    @PatchMapping("/{adminId}/status")
    public ResponseEntity<UpdateStatusResponseDto> updateStatus(
            @PathVariable Long adminId,
            @RequestBody @Valid UpdateStatusRequestDto request
    ) {
        UpdateStatusResponseDto result = adminService.updateStatus(adminId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────관리자 삭제────────────────────────────────────
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId)
    {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //────────────────────────────────────관리자 승인────────────────────────────────────
    @PatchMapping("/{adminId}/approve")
    public ResponseEntity<Void> approveAdmin(@PathVariable Long adminId) {
        adminService.approveAdmin(adminId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //────────────────────────────────────관리자 거부────────────────────────────────────
    @PatchMapping("/{adminId}/reject")
    public ResponseEntity<Void> rejectAdmin(@PathVariable Long adminId,
                                            @Valid @RequestBody RejectAdminRequestDto request) {
        adminService.rejectAdmin(adminId,request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //────────────────────────────────────내프로필 조회────────────────────────────────────
    @GetMapping("/me")
    public ResponseEntity<GetMyInfoResponseDto> getInfo(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin
    )
    {
        GetMyInfoResponseDto result = adminService.getMyInfo(loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────내프로필 수정────────────────────────────────────
    @PatchMapping("/me")
    public ResponseEntity<UpdateMyInfoResponseDto> updateInfo(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid @RequestBody UpdateMyInfoRequestDto request
    )
    {
        UpdateMyInfoResponseDto result = adminService.updateMyInfo(loginAdmin,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //────────────────────────────────────내프로필 수정────────────────────────────────────
    @PatchMapping("/me/password")
    public ResponseEntity<String> changePassword(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid @RequestBody ChangePasswordRequestDto request)
    {
        adminService.changePassword(loginAdmin,request);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

}
