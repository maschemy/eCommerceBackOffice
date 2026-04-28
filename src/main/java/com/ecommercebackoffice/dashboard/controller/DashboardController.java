package com.ecommercebackoffice.dashboard.controller;


import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import com.ecommercebackoffice.dashboard.dto.DashboardResponseDto;
import com.ecommercebackoffice.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@RequestMapping("dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDto> getDashboard(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin
    ){
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.getDashboard());
    }
}
