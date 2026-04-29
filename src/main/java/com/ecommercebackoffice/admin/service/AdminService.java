package com.ecommercebackoffice.admin.service;

import com.ecommercebackoffice.admin.dto.*;
import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.exception.AdminNotFoundException;
import com.ecommercebackoffice.common.exception.AdminPermissionException;
import com.ecommercebackoffice.common.exception.PasswordIncorrectException;
import com.ecommercebackoffice.common.exception.UsedEmailException;
import com.ecommercebackoffice.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    //────────────────────────────────────관리자생성────────────────────────────────────
    @Transactional
    public CreateAdminResponseDto save(CreateAdminRequestDto request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new UsedEmailException("이미 존재하는 이메일입니다."); //이메일 중복검사
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword()); //비밀번호암호화

        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getPhoneNumber(),
                request.getRole()
        );

        Admin saved = adminRepository.save(admin);

        return new CreateAdminResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPhoneNumber(),
                saved.getRole(),
                saved.getStatus(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    //────────────────────────────────────전체조회────────────────────────────────────
    @Transactional(readOnly = true)
    public Page<SearchAdminResponseDto> getAll(SearchAdminRequestDto request) {

        Pageable pageable = PageRequest.of(
                request.getPage() - 1, // 페이지 0부터시작
                request.getSize(),
                Sort.by(
                        Sort.Direction.fromString(request.getDirection()),
                        request.getSortBy()
                )
        );
        String keyword = request.getKeyword();

        Page<Admin> admins =
                adminRepository.searchNameOrEmail(
                        keyword,
                        pageable
                );

        return admins.map(admin -> new SearchAdminResponseDto(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getModifiedAt()
        ));
    }

    //────────────────────────────────────상세조회────────────────────────────────────
    @Transactional(readOnly = true)
    public GetOneAdminResponseDto getOne(Long adminId) {
        Admin admin = findAdminId(adminId);

        return new GetOneAdminResponseDto(admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getModifiedAt());
    }

    //────────────────────────────────────관리자 정보 수정────────────────────────────────────
    @Transactional
    public UpdateAdminResponseDto update(Long adminId, UpdateAdminRequestDto request) {

        Admin admin = findAdminId(adminId);
        if (!admin.getEmail().equals(request.getEmail()) && adminRepository.existsByEmail(request.getEmail())) {
            throw new UsedEmailException("이미 존재하는 이메일입니다."); //회원가입때 중복검사와다르다
        }

        admin.adminUpdate(request.getName(), request.getEmail(), request.getPhoneNumber());

        return new UpdateAdminResponseDto(admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getModifiedAt());
    }

    //────────────────────────────────────관리자 역활 수정────────────────────────────────────
    @Transactional
    public UpdateRoleResponseDto updateRole(LoginAdmin loginAdmin, Long adminId, UpdateRoleRequestDto request) {
        Admin admin = findAdminId(loginAdmin.adminId());
        if (loginAdmin.adminId().equals(adminId)) {
            throw new AdminPermissionException("본인의 역할은 수정할 수 없습니다.");
        }
        admin.roleChange(request.getRole());

        return new UpdateRoleResponseDto(admin.getRole());
    }

    //────────────────────────────────────관리자 상태 수정────────────────────────────────────
    @Transactional
    public UpdateStatusResponseDto updateStatus(LoginAdmin loginAdmin, Long adminId, UpdateStatusRequestDto request) {
        Admin admin = findAdminId(loginAdmin.adminId());
        if (loginAdmin.adminId().equals(adminId)) {
            throw new AdminPermissionException("본인의 상태은 수정할 수 없습니다.");
        }
        admin.statusChange(request.getStatus());

        return new UpdateStatusResponseDto(admin.getStatus());
    }

    //────────────────────────────────────관리자 삭제────────────────────────────────────
    @Transactional
    public void deleteAdmin(LoginAdmin loginAdmin,Long adminId) {

        if (loginAdmin.adminId().equals(adminId)) {
            throw new AdminPermissionException("슈퍼관리자는 삭제할 수 없습니다.");
        }

        Admin admin = findAdminId(adminId);
        admin.delete(); //소프트 삭제
    }

    //────────────────────────────────────내 정보 조회────────────────────────────────────
    @Transactional(readOnly = true)
    public GetMyInfoResponseDto getMyInfo(LoginAdmin loginAdmin) {
        Admin admin = findAdminId(loginAdmin.adminId());

        return new GetMyInfoResponseDto(admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber());
    }

    //────────────────────────────────────내 정보 수정────────────────────────────────────
    @Transactional
    public UpdateMyInfoResponseDto updateMyInfo(LoginAdmin loginAdmin, UpdateMyInfoRequestDto request) {
        Admin admin = findAdminId(loginAdmin.adminId());
        admin.adminUpdate(request.getName(), request.getEmail(), request.getPhoneNumber());
        return new UpdateMyInfoResponseDto(admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getModifiedAt());
    }

    //────────────────────────────────────비밀번호 변경────────────────────────────────────
    @Transactional
    public void changePassword(LoginAdmin loginAdmin, ChangePasswordRequestDto request) {
        Admin admin = findAdminId(loginAdmin.adminId());

        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            throw new PasswordIncorrectException("비밀번호가 올바르지 않습니다.");
        }
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new PasswordIncorrectException("기존 비밀번호와 동일한 비밀번호입니다.");
        }

        String encoded = passwordEncoder.encode(request.getNewPassword());

        admin.passwordChange(encoded);

    }

    //────────────────────────────────────관리자 승인────────────────────────────────────
    @Transactional
    public void approveAdmin(Long adminId) {
        Admin admin = findAdminId(adminId);
        admin.approve();
    }

    //────────────────────────────────────관리자 거절────────────────────────────────────
    @Transactional
    public void rejectAdmin(Long adminId, RejectAdminRequestDto request) {
        Admin admin = findAdminId(adminId);
        admin.reject(request.getReject());
    }

    //────────────────────────────────────메소드────────────────────────────────────
    private Admin findAdminId(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 Admin 입니다."));
    }
}
