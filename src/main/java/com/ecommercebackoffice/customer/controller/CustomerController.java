package com.ecommercebackoffice.customer.controller;

import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import com.ecommercebackoffice.customer.dto.*;
import com.ecommercebackoffice.customer.entity.CustomerStatus;
import com.ecommercebackoffice.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;



    /**
     * 고객 목록 조회
     * 키워드, 상태, 페이징, 정렬 조건 지원
     * @param keyword (이름, 이메일) - 없으면 전체 조회
     * @param status 상태 필터(ACTIVE / INACTIVE / SUSPENDED) - 없으면 전체 조회)
     * @param page 페이지 번호 ( 기본값: 1)
     * @param size 페이지당 개수 (기본값: 10)
     * @param sortBy 정렬 기준 (기본값: createdAt )
     * @param direction 정렬 방향 ( 기본값: desc )
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<SearchCustomerResponseDto>> getAllCustomer(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAll(keyword, status, page, size, sortBy, direction));
    }

    /**
     * 고객 단건 조회
     * @param customerId
     * @return
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<SearchCustomerResponseDto> getOneCustomer(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findOne(customerId));
    }

    /**
     * 고객 상정보 수정(이름, 이메일, 전화번호)
     * @param request
     * @param customerId
     * @return
     */
    @PatchMapping("/{customerId}")
    public ResponseEntity<UpdateCustomerResponseDto> updateCustomerInfo(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid
            @RequestBody UpdateCustomerRequestDto request,
            @PathVariable Long customerId
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateInfo(request, customerId));
    }

    /**
     * 고객 상태 변경(활성 / 비활성 / 정지)
     * @param request
     * @param customerId
     * @return
     */
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<UpdateCustomerStatusResponseDto> updateCustomerStatus(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @Valid @RequestBody UpdateCustomerStatusRequestDto request,
            @PathVariable Long customerId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateStatus(request,customerId));
    }

    /**
     * 고객 삭제
     * @param customerId
     * @return
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerId(
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin,
            @PathVariable Long customerId){
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
