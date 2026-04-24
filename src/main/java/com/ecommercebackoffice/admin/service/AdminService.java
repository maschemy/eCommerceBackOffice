package com.ecommercebackoffice.admin.service;

import com.ecommercebackoffice.admin.dto.CreateAdminRequestDto;
import com.ecommercebackoffice.admin.dto.CreateAdminResponseDto;
import com.ecommercebackoffice.admin.dto.SearchAdminRequestDto;
import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.config.PasswordEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateAdminResponseDto save(@Valid CreateAdminRequestDto request) {

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

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

    @Transactional(readOnly = true)
    public SearchAdminRequestDto getAll(@Valid SearchAdminRequestDto request) {
    }
}
