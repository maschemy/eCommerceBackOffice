package com.ecommercebackoffice.auth.controller;

import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.auth.dto.LoginRequest;
import com.ecommercebackoffice.auth.dto.LoginResponse;
import com.ecommercebackoffice.auth.service.AuthService;
import com.ecommercebackoffice.config.Const;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session)
    {
        LoginResponse result = authService.login(request);

        session.setAttribute(Const.LOGIN_ADMIN,
                new LoginAdmin(result.getId(),result.getEmail(),result.getRole()));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = Const.LOGIN_ADMIN, required = false)LoginAdmin loginAdmin, HttpSession session)
    {
        if(loginAdmin == null){ //세션없는상태에선 에러메세지
            return ResponseEntity.badRequest().build();
        }
        session.invalidate(); // 세션 삭제 , 로그인 해제
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
