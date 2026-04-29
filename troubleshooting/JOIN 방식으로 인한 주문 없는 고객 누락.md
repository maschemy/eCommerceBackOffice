# 🛠️ Trouble Shooting: JOIN 방식으로 인한 주문 없는 고객 누락

---

## 📌 배경 (Background)
* **상황:** 고객 목록 조회 응답에 각 고객의 '총 주문 수'와 '총 구매 금액'을 포함하는 기능을 구현 중.
* **이슈:** 더미 데이터상 고객은 3명이지만, 실제 주문 이력이 있는 고객 1명만 목록에 조회되는 현상 발견.
* **원인:** 기존 `JOIN` 방식이 주문 이력이 없는 고객을 결과에서 제외시키고 있음을 인지.

---

## 🧐 JOIN vs LEFT JOIN
* **JOIN (INNER JOIN):** 두 테이블에서 일치하는 행만 결과에 포함합니다. (주문 이력이 있는 고객만 조회 ❌)
* **LEFT JOIN:** 왼쪽 테이블(고객)의 모든 행을 포함하고, 오른쪽 테이블(주문)에 일치하는 데이터가 없으면 `null`로 채웁니다. (주문 여부와 관계없이 전체 고객 조회 ✅)

---

## 💡 해결 방안: LEFT JOIN & COALESCE 적용
`LEFT JOIN`을 사용하여 모든 고객을 결과에 포함하고, `COALESCE` 함수를 사용하여 주문이 없는 고객의 집계값을 `null`이 아닌 `0`으로 반환하도록 처리했습니다.

```java
@Query("SELECT c, COUNT(o), COALESCE(SUM(o.totalPrice), 0) FROM Customer c " +
       "LEFT JOIN Order o ON o.customer = c " +  // LEFT JOIN으로 변경
       "WHERE c.deletedAt IS NULL " +
       "AND (:keyword IS NULL OR c.name LIKE %:keyword% OR c.email LIKE %:keyword%) " +
       "AND (:status IS NULL OR c.status = :status) " +
       "GROUP BY c")
Page<Object[]> findAllWithFilter(...);
```

---

## 📝 결말 및 배운 점 (Lesson Learned)
* **결과:** `LEFT JOIN`과 `COALESCE` 적용으로 주문이 없는 고객도 `totalOrderCount: 0`, `totalOrderAmount: 0`으로 정상 응답됨.
* **배운 점:** 집계 쿼리 작성 시, 기준이 되는 테이블의 모든 데이터를 보존해야 하는지(전체 목록 조회인지) 여부를 먼저 확인하고 `JOIN` 방식을 신중하게 선택하는 습관이 필요함을 체감.
```