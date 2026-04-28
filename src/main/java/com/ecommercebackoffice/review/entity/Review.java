package com.ecommercebackoffice.review.entity;

import com.ecommercebackoffice.common.entity.BaseEntity;
import com.ecommercebackoffice.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰는 하나의 주문에 속한다
    // FetchType.LAZY: 리뷰를 조회할 때 주문 정보를 즉시 가져오지 않고 실제로 사용할 때 가져온다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 평점
    @Column(nullable = false)
    private Integer rating;

    // 리뷰 내용
    @Column(nullable = false)
    private String content;

    // 삭제일
    // 삭제일만 기록하는 소프트 삭제 방식
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Review(Order order, Integer rating, String content) {
        this.order = order;
        this.rating = rating;
        this.content = content;
    }

    // 소프트 삭제 메서드
    // deletedAt에 현재 시간 기록
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
