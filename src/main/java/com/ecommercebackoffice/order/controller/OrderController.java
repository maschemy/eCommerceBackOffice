package com.ecommercebackoffice.order.controller;

import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.order.dto.*;
import com.ecommercebackoffice.order.entity.OrderStatus;
import com.ecommercebackoffice.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * CS 주문 생성
     * @param request customerId, productId, quantity 요청
     * @param loginAdmin 로그인 여부 확인
     * @return HttpStatus 코드, 응답 dto 반환
     */
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN','CS_ADMIN')")
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @Valid @RequestBody CreateOrderRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
            )
    {
        CreateOrderResponseDto result = orderService.save(request, loginAdmin);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 주문 리스트 조회(필터, 정렬, 페이징)
     * @param page 조회할 페이지 번호, 기본값 = 1
     * @param size 페이지 당 주문 개수, 기본값 = 10
     * @param orderNumberOrCustomerName 주문번호 또는 고객명으로 검색
     * @param sortBy 정렬 기준 ("orderNumber", "customer", "product", "quantity",
     *              "totalPrice", "createdAt", "status)
     * @param direction 정렬 방식 (ASC, DESC)
     * @param status 상태 필터, orderStatus (READY, DELIVERY, COMPLETE)
     * @param loginAdmin 로그인 여부 확인
     * @return HttpStatus 코드, 응답 dto 반환
     */
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN','CS_ADMIN')")
    @GetMapping
    public ResponseEntity<PaginationOrderDTO> getAllOrder(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", required = false) String orderNumberOrCustomerName,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction,
            @RequestParam(name = "status", required = false) OrderStatus status,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    )
    {
        PaginationOrderDTO result = orderService.getAll(page, size, orderNumberOrCustomerName, sortBy, direction, status);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 주문 상세 조회
     * @param id 주문 id
     * @param loginAdmin 로그인 여부 확인
     * @return HttpStatus 코드, 응답 dto 반환
     */
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN','CS_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReadOneOrderResponseDto> getOneOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {

        ReadOneOrderResponseDto result = orderService.getOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 주문 상태 수정 (준비중 -> 배송중 -> 배송완료)
     * @param id 주문 id
     * @param request status 요청(READY, DELIVERY, COMPLETE)
     * @param loginAdmin 로그인 여부 확인
     * @return HttpStatus 코드, 응답 dto 반환
     */
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN','CS_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateOrderResponseDto> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        UpdateOrderResponseDto result = orderService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 주문 취소
     * @param id 주문 id
     * @param request cancelReason 요청, 필수값
     * @param loginAdmin 로그인 여부 확인
     * @return HttpStatus 코드, 응답 dto 반환
     */
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN','CS_ADMIN')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<CancelOrderResponseDto> cancelOrder(
            @PathVariable Long id,
            @Valid @RequestBody CancelOrderRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        CancelOrderResponseDto result = orderService.cancel(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
