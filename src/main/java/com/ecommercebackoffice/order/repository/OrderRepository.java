package com.ecommercebackoffice.order.repository;

import com.ecommercebackoffice.order.entity.Order;
import com.ecommercebackoffice.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 주문 리스트 전체 조회(정렬, 필터, 페이징)
     * 고객, 상품 테이블 join fetch
     * 관리자 테이블 left join (cs 주문의 경우에만 관리자 정보 포함)
     * 파라미터 없을 경우 전체 조회
     * @param keyword 고객명, 주문번호로 검색(필터)
     * @param status 주문 상태 필터 (READY, DELIVERY, COMPLETE)
     * @param pageable 페이지 정보
     * @return 페이지
     */
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

    /**
     * 주문 id로 단건 조회
     * @param id must not be {@literal null}.
     * @return id 동일한 주문 정보
     */
    @Override
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.customer c " +
            "JOIN FETCH o.product p " +
            "LEFT JOIN FETCH o.admin a " +
            "WHERE o.id = :id")
    Optional<Order> findById(Long id);

    // 오늘 주문 수
    @Query("SELECT COUNT(o) FROM Order o where DATE(o.createdAt) = CURRENT_DATE")
    long countTodayOrders();

    // 총 매출
    @Query("SELECT COALESCE(sum(o.totalPrice), 0) FROM Order o WHERE o.status != :status")
    Long sumTotalRevenue(@Param("status") OrderStatus status);

    // 오늘 총 매출
    @Query("SELECT  COALESCE(sum(o.totalPrice), 0) FROM Order o WHERE DATE(o.createdAt) = current_date AND o.status != :status")
    Long sumTodayRevenue(@Param("status") OrderStatus status);

    // 상태에 따른 주문 개수
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countStatusOrders(@Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.customer "+
            "JOIN FETCH o.product " +
            "ORDER BY o.createdAt DESC"
    )
    List<Order> findRecentOrders(Pageable pageable);
}
