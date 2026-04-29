package com.ecommercebackoffice.review.repository;

import com.ecommercebackoffice.review.dto.ReviewStatistics;
import com.ecommercebackoffice.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 키워드(고객명, 상품명), 평점 필터, 삭제되지 않은 리뷰만 조회
    // 소프트 삭제된 리뷰는 제외
    @Query("SELECT r FROM Review r " +
           "JOIN FETCH r.order o " +           // 주문과 조인
           "JOIN FETCH o.customer c " +        // 고객과 조인
           "JOIN FETCH o.product p " +         // 상품과 조인
           "WHERE r.deletedAt IS NULL " + // 삭제되지 않은 리뷰만
           "AND (:keyword IS NULL OR c.name LIKE %:keyword% OR p.name LIKE %:keyword%) " +
           "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> findByConditions(
            @Param("keyword") String keyword,
            @Param("rating") Integer rating,
            Pageable pageable);

    // 상품 id로 최신 리뷰 3개 찾기
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.order o " +           // 주문과 조인
            "JOIN FETCH o.customer c " +        // 고객과 조인
            "WHERE r.deletedAt IS NULL " +
            "AND o.product.id = :productId " +
            "ORDER BY r.createdAt DESC " +
            "limit 3")
    List<Review> findLatestReviewsByProductId(@Param("productId") Long productId);

    // 요구사항 통계 가져오기
    @Query("SELECT new com.ecommercebackoffice.review.dto.ReviewStatistics(" +
            "AVG(r.rating * 1.0), " + // 1. ratingAverage (Double 타입 보장)
            "COUNT(r), " +            // 2. totalReview
            "COUNT(CASE WHEN r.rating = 5 THEN 1 END), " + // 3. fiveRatings
            "COUNT(CASE WHEN r.rating = 4 THEN 1 END), " + // 4. fourRatings
            "COUNT(CASE WHEN r.rating = 3 THEN 1 END), " + // 5. threeRatings
            "COUNT(CASE WHEN r.rating = 2 THEN 1 END), " + // 6. twoRatings
            "COUNT(CASE WHEN r.rating = 1 THEN 1 END)) " + // 7. oneRatings
            "FROM Review r " +
            "JOIN r.order o " +
            "WHERE r.deletedAt IS NULL " +
            "AND o.product.id = :productId")
    ReviewStatistics getReviewStatistics(@Param("productId") Long productId);
}
