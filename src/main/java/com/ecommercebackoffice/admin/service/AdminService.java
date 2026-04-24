package com.ecommercebackoffice.admin.service;

import com.ecommercebackoffice.admin.dto.CreateAdminRequestDto;
import com.ecommercebackoffice.admin.dto.CreateAdminResponseDto;
import com.ecommercebackoffice.admin.dto.SearchAdminRequestDto;
import com.ecommercebackoffice.admin.dto.SearchAdminResponseDto;
import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.common.exception.UsedEmailException;
import com.ecommercebackoffice.config.PasswordEncoder;
import jakarta.validation.Valid;
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
            throw new UsedEmailException("이미 사용중인 이메일입니다."); //이메일 중복검사
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
                request.getPage() - 1, // 0부터시작
                request.getSize(),
                Sort.by(
                        Sort.Direction.fromString(request.getDirection()),
                        request.getSortBy()
                )
        );

        Page<Admin> admins = adminRepository.findByNameContainingOrEmailContaining(
                request.getName(),
                request.getEmail(),
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
}
