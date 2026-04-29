# 🛠️ Trouble Shooting: 리뷰 관리 (소프트 삭제 조회 처리)

---

## 📌 배경 (Background)
* **상황:** 소프트 삭제(`Soft Delete`)가 적용된 리뷰 시스템에서 데이터를 조회하는 기능 구현.
* **이슈:** `deleted_at` 컬럼에 삭제 시간이 기록되어 있음에도 불구하고, 삭제된 리뷰가 조회 결과에 계속 포함되는 문제 발생.

---

## 🔍 원인 분석 (Root Cause)
* **현상:** 데이터베이스 쿼리 수행 시 삭제 여부를 판별하는 `deleted_at` 컬럼에 대한 필터링 조건이 누락됨.

---

## ✅ 해결 방안 (Solution)

### 1. 리스트 조회 쿼리 수정
JPQL에 `WHERE r.deletedAt IS NULL` 조건을 추가하여 삭제되지 않은(값이 `null`인) 리뷰만 조회하도록 수정했습니다.

```java
@Query("SELECT r FROM Review r " +
       "JOIN r.order o " +
       "JOIN r.customer c " +
       "JOIN r.product p " +
       "WHERE r.deletedAt IS NULL " +           // ← 소프트 삭제된 리뷰 제외
       "AND (:keyword IS NULL OR c.name LIKE %:keyword% OR p.name LIKE %:keyword%) " +
       "AND (:rating IS NULL OR r.rating = :rating)")
Page<Review> findByConditions(
        @Param("keyword") String keyword,
        @Param("rating") Integer rating,
        Pageable pageable
);
```

***

### 2. 단건 조회 필터링 적용
단건 조회 시에도 `Optional.filter()`를 활용하여 이미 삭제된 데이터(`deletedAt`이 `null`이 아님)는 조회되지 않도록 예외 처리 로직을 보강했습니다.

```java
private Review findReviewById(Long id) {
    return reviewRepository.findById(id)
            .filter(review -> review.getDeletedAt() == null)  // ← 삭제된 리뷰 제외
            .orElseThrow(() -> new IllegalStateException("리뷰를 찾을 수 없습니다. ID: " + id));
}
```
