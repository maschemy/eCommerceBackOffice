package com.ecommercebackoffice.order.repository;

import com.ecommercebackoffice.order.entity.Order;
import com.ecommercebackoffice.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.customer c " +
            "JOIN FETCH o.product p " +
            "LEFT JOIN FETCH o.admin a " +
            "WHERE (:keyword IS NULL OR o.orderNumber LIKE %:keyword% " +
            "      OR c.name LIKE %:keyword%) " +
            "AND (:status IS NULL OR o.status = :status)")
    Page<Order> findAllWithFilters(
            @Param("keyword") String keyword,
            @Param("status") OrderStatus status,
            Pageable pageable
    );

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.customer c " +
            "JOIN FETCH o.product p " +
            "LEFT JOIN FETCH o.admin a " +
            "WHERE o.id = :id")
    Optional<Order> findById(Long id);
}
