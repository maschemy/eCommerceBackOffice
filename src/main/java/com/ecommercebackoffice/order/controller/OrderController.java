package com.ecommercebackoffice.order.controller;

import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import com.ecommercebackoffice.order.dto.*;
import com.ecommercebackoffice.order.entity.OrderStatus;
import com.ecommercebackoffice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // CS 주문 생성
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @RequestBody CreateOrderRequestDto request,
            @SessionAttribute(name = Const.LOGIN_ADMIN, required = false)LoginAdmin loginAdmin
            )
    {
        CreateOrderResponseDto result = orderService.save(request, loginAdmin);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 주문 리스트 조회
    @GetMapping
    public ResponseEntity<PaginationOrderDTO> getAllOrder(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", required = false) String orderNumberOrCustomerName,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction,
            @RequestParam(name = "status", required = false) OrderStatus status,
            @SessionAttribute(name = Const.LOGIN_ADMIN, required = false)LoginAdmin loginAdmin
    )
    {
        PaginationOrderDTO result = orderService.getAll(page, size, orderNumberOrCustomerName, sortBy, direction, status);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 주문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReadOneOrderResponseDto> getOneOrder(
            @PathVariable Long id
    ) {

        ReadOneOrderResponseDto result = orderService.getOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
