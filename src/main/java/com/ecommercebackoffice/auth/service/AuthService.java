package com.ecommercebackoffice.auth.service;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.auth.dto.LoginRequest;
import com.ecommercebackoffice.auth.dto.LoginResponse;
import com.ecommercebackoffice.config.PasswordEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {

        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다."));

        //암호화된 DB속 비밀번호와 입력값 비교
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) { //equals 아닌 matches 로 비교
            throw new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        admin.getStatus().validateLogin();


        return new LoginResponse(admin.getId(),
                admin.getEmail(),
                admin.getRole());
    }
}
