package com.ecommercebackoffice.order.service;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.exception.QuantityGreaterThanStockException;
import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.customer.repository.CustomerRepository;
import com.ecommercebackoffice.order.dto.*;
import com.ecommercebackoffice.order.entity.Order;
import com.ecommercebackoffice.order.entity.OrderStatus;
import com.ecommercebackoffice.order.repository.OrderRepository;
import com.ecommercebackoffice.product.entity.Product;
import com.ecommercebackoffice.product.entity.ProductStatus;
import com.ecommercebackoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    // CS 주문 생성
    @Transactional
    public CreateOrderResponseDto save(CreateOrderRequestDto request, LoginAdmin loginAdmin) {
        Admin admin = adminRepository.findById(loginAdmin.adminId()).orElseThrow(
                () -> new IllegalStateException("없는 관리자"));
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new IllegalStateException("없는 고객"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new IllegalStateException("없는 상품"));

        // 상품이 판매중이 아닐 경우 예외 발생
        if (product.getStatus() != ProductStatus.ON_SALE) {
            throw new IllegalStateException("상품이 " + product.getStatus().getDescription() + "입니다.");
        }

        // 재고가 주문 수량보다 부족한 경우 예외 발생
        if (product.getStock() < request.getQuantity()) {
            throw new QuantityGreaterThanStockException("담은 수량이 재고보다 많습니다. 재고:"
                    + product.getStock());
        }

        // 추후 재고 차감 처리 로직 구현

        // 주문번호 생성 -> 예시) ORD-20260424-0001
        String orderNumber = "ORD" + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-"
                + UUID.randomUUID().toString().substring(0, 4);

        // 총 주문 금액 계산
        Integer totalPrice = product.getPrice() * request.getQuantity();

        // 요청에 따른 주문 생성
        Order order = new Order(
                admin,
                customer,
                product,
                orderNumber,
                request.getQuantity(),
                totalPrice
        );

        // db 저장 후 요청 dto로 변환 후 반환
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponseDto(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                customer.getName(),
                product.getName(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getStatus(),
                savedOrder.getCreatedAt(),
                admin.getName()
        );
    }

    // 주문 리스트 조회
    @Transactional(readOnly = true)
    public PaginationOrderDTO getAll(int page, int size,
                                     String orderNumberOrCustomerName,
                                     String sortBy, String direction,
                                     OrderStatus status) {

        // 정렬 기준 리스트
        List<String> sortableColumns = new ArrayList<>(List.of("orderNumber", "customer",
                "product", "quantity", "totalPrice", "createdAt", "status")) ;

        // 정렬 기준 검증
        if (!sortableColumns.contains(sortBy)) {
            throw new IllegalStateException("잘못된 정렬 기준");
        }

        // 정렬 방식 검증
        if (!direction.equals("ASC") && !direction.equals("DESC")) {
            throw new IllegalStateException("ASC 또는 DESC로 입력(대소문자 구분): " + direction);
        }

        // 페이지 정보 검증
        if (page < 0 || size < 0) {
            throw new IllegalStateException("page와 size는 음수가 될 수 없습니다 page: "
                    + page + "size: " + size);
        }

        // 페이지네이션
        Page<Order> orders = orderRepository.findAllWithFilters(
                orderNumberOrCustomerName,
                status,
                PageRequest.of(page-1, size, Sort.by(Sort.Direction.fromString(direction), sortBy)));

        // 페이지 내용 dto로 변환
        List<ReadAllOrdersResponseDto> readAllOrdersResponseDtos = orders
                    .stream()
                    .map(order -> new ReadAllOrdersResponseDto(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getCustomer().getName(),
                        order.getProduct().getName(),
                        order.getQuantity(),
                        order.getTotalPrice(),
                        order.getStatus(),
                        order.getCreatedAt(),
                        order.getAdmin().getName()))
                    .toList();

        return new PaginationOrderDTO(readAllOrdersResponseDtos, page, size, orders.getTotalElements(), orders.getTotalPages());
    }

    // 주문 상세 조회
    @Transactional(readOnly = true)
    public ReadOneOrderResponseDto getOne(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 주문")
        );

        return new ReadOneOrderResponseDto(
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getAdmin().getName(),
                order.getAdmin().getEmail(),
                order.getAdmin().getRole()
        );
    }

    // 주문 상태 수정(준비중 -> 배송중 -> 배송완료)
    @Transactional
    public UpdateOrderResponseDto update(Long id, UpdateOrderRequestDto request) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 주문")
        );

        if (order.getStatus() == OrderStatus.COMPLETE) {
            throw new IllegalStateException("배송완료된 주문은 상태를 변경할 수 없습니다");
        }

        // Enum에 없는 status 입력 시 예외 발생
        if (OrderStatus.valueOf(request.getStatus().name()) == null) {
            throw new IllegalStateException("올바른 주문 상태를 입력해야 합니다");
        }

        order.update(request.getStatus());

        return new UpdateOrderResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getModifiedAt()
        );
    }

    // 주문 취소
    @Transactional
    public CancelOrderResponseDto cancel(Long id, CancelOrderRequestDto request) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 주문")
        );

        order.cancel(request.getCancelReason());

        return new CancelOrderResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getCancelReason(),
                order.getModifiedAt()
        );
    }
}
