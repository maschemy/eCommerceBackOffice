package com.ecommercebackoffice.dashboard.controller;


import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import com.ecommercebackoffice.dashboard.dto.DashboardResponseDto;
import com.ecommercebackoffice.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@PreAuthorize("isAuthenticated()")
@RestController
@RequiredArgsConstructor
@RequestMapping("dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    /**
     * 대쉬보드 출력
     * @param loginAdmin
     * @return
     */
    @GetMapping
    public ResponseEntity<DashboardResponseDto> getDashboard(
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ){
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.getDashboard());
    }
}
