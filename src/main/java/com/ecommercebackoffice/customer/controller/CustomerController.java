package com.ecommercebackoffice.customer.controller;

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
     * @param keyword
     * @param status
     * @param page
     * @param size
     * @param sortBy
     * @param direction
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<SearchCustomerResponseDto>> getAllCustomer(
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
    public ResponseEntity<SearchCustomerResponseDto> getOneCustomer(@PathVariable Long customerId){
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
    public ResponseEntity<Void> deleteCustomerId(@PathVariable Long customerId){
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
