package com.ecommercebackoffice.review.repository;

import com.ecommercebackoffice.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 키워드(고객명, 상품명), 평점 필터, 삭제되지 않은 리뷰만 조회
    // 소프트 삭제된 리뷰는 제외
    @Query("SELECT r FROM Review r " +
           "JOIN r.order o " +           // 주문과 조인
           "JOIN o.customer c " +        // 고객과 조인
           "JOIN o.product p " +         // 상품과 조인
           "WHERE r.deletedAt IS NULL " + // 삭제되지 않은 리뷰만
           "AND (:keyword IS NULL OR c.name LIKE %:keyword% OR p.name LIKE %:keyword%) " +
           "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> findByConditions(
            @Param("keyword") String keyword,
            @Param("rating") Integer rating,
            Pageable pageable);
}
