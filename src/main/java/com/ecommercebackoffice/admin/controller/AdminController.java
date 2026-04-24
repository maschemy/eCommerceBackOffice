package com.ecommercebackoffice.admin.controller;

import com.ecommercebackoffice.admin.dto.CreateAdminRequestDto;
import com.ecommercebackoffice.admin.dto.CreateAdminResponseDto;
import com.ecommercebackoffice.admin.dto.SearchAdminRequestDto;
import com.ecommercebackoffice.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<CreateAdminResponseDto> createAdmin(@Valid @RequestBody CreateAdminRequestDto request)
    {
        CreateAdminResponseDto result = adminService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<SearchAdminRequestDto> getAllAdim(@Valid SearchAdminRequestDto request)
    {
        SearchAdminRequestDto result = adminService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
