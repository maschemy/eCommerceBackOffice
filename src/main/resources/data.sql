INSERT INTO admins (name, email, password, phone_number, role, status, created_at, modified_at)
VALUES ('테스트관리자',
        'admin@test.com',
        '$2a$04$8Nnn8w7qFb2Y6ADgO2N6zuvmg03s2kimiXDiMU4RjpXwKRZ/f8Zyq',
        '010-0000-0000',
        'SUPER_ADMIN', 'ACTIVE', NOW(), NOW());

-- 1. 외래 키 제약 조건으로 인한 오류 방지
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE products;
TRUNCATE TABLE admins;
TRUNCATE TABLE customers;
TRUNCATE TABLE orders;
TRUNCATE TABLE reviews;
SET FOREIGN_KEY_CHECKS = 1;

-- 2. Admins 데이터 (기존 1번 + 맨 밑에 있던 테스트관리자 통합)
INSERT INTO admins (created_at, id, modified_at, email, name, password, phone_number, role, status)
VALUES
    ('2026-04-24 19:15:17', 1, NULL, 'kim@k.k', 'Kim', '123456778', '010-0000-1111', 'SUPER_ADMIN', 'ACTIVE'),
    (NOW(), 2, NOW(), 'admin@test.com', '테스트관리자', '$2a$04$8Nnn8w7qFb2Y6ADgO2N6zuvmg03s2kimiXDiMU4RjpXwKRZ/f8Zyq', '010-9999-9999', 'SUPER_ADMIN', 'ACTIVE');

-- 3. Products 데이터
INSERT INTO products (price, stock, admin_id, created_at, id, modified_at, name, category, status)
VALUES
    (1000, 5, 1, '2026-04-24 19:17:09', 1, NULL, 'computer', 'ELECTRONICS', 'ON_SALE'),
    (2000, 5, 1, '2026-04-24 19:17:09', 2, NULL, 'phone', 'ELECTRONICS', 'ON_SALE');

-- 4. Customers 데이터 (🔥수정: 전화번호 중복 방지)
INSERT INTO customers (created_at, deleted_at, id, modified_at, email, name, phone_number, status)
VALUES
    ('2026-04-24 19:16:15', NULL, 1, NULL, 'park@kkk.kkk', 'Park', '010-1111-1111', 'ACTIVE'),
    ('2026-04-24 19:16:15', NULL, 2, NULL, 'kim@kkk.kkk', 'Kim', '010-2222-2222', 'ACTIVE');

-- 5. Orders 데이터 (🔥수정: 리뷰 10개를 예쁘게 담기 위해 주문을 10개로 생성)
INSERT INTO orders (id, admin_id, customer_id, product_id, order_number, quantity, total_price, status, cancel_reason, created_at, modified_at)
VALUES
    (1, 1, 1, 1, 'ORD-20260429-001', 1, 1000, 'COMPLETE', NULL, '2026-04-29 12:13:00', NULL),
    (2, 1, 2, 2, 'ORD-20260429-002', 1, 2000, 'COMPLETE', NULL, '2026-04-29 12:15:00', NULL),
    (3, 1, 1, 1, 'ORD-20260429-003', 2, 2000, 'COMPLETE', NULL, '2026-04-29 12:20:00', NULL),
    (4, 1, 2, 2, 'ORD-20260429-004', 1, 2000, 'COMPLETE', NULL, '2026-04-29 12:25:00', NULL),
    (5, 1, 1, 1, 'ORD-20260429-005', 1, 1000, 'COMPLETE', NULL, '2026-04-29 12:30:00', NULL),
    (6, 1, 2, 1, 'ORD-20260429-006', 1, 1000, 'COMPLETE', NULL, '2026-04-29 12:35:00', NULL),
    (7, 1, 1, 2, 'ORD-20260429-007', 1, 2000, 'COMPLETE', NULL, '2026-04-29 12:40:00', NULL),
    (8, 1, 2, 1, 'ORD-20260429-008', 3, 3000, 'COMPLETE', NULL, '2026-04-29 12:45:00', NULL),
    (9, 1, 1, 2, 'ORD-20260429-009', 1, 2000, 'COMPLETE', NULL, '2026-04-29 12:50:00', NULL),
    (10, 1, 2, 1, 'ORD-20260429-010', 1, 1000, 'COMPLETE', NULL, '2026-04-29 12:55:00', NULL);

-- 6. Reviews 데이터 (🔥수정: 위에서 만든 1~10번 주문에 하나씩 1:1로 매핑)
INSERT INTO reviews (id, order_id, rating, content, created_at, modified_at, deleted_at)
VALUES
    (1, 1, 5, '정말 만족스럽습니다! 배송도 빠르고 품질도 좋아요.', '2026-04-25 10:00:00', NULL, NULL),
    (2, 2, 4, '괜찮네요. 쓸만합니다.', '2026-04-25 11:30:00', NULL, NULL),
    (3, 3, 5, '적극 추천합니다. 가성비 최고예요.', '2026-04-25 14:15:22', NULL, NULL),
    (4, 4, 3, '그럭저럭입니다. 생각보다 크기가 작아요.', '2026-04-26 09:10:10', NULL, NULL),
    (5, 5, 5, '포장도 깔끔하고 너무 마음에 듭니다!', '2026-04-26 16:45:00', NULL, NULL),
    (6, 6, 2, '배송이 너무 늦어서 아쉬웠어요.', '2026-04-27 12:05:30', NULL, NULL),
    (7, 7, 4, '선물용으로 샀는데 받는 분이 좋아하시네요.', '2026-04-27 18:20:15', NULL, NULL),
    (8, 8, 5, '재구매 의사 100% 입니다.', '2026-04-28 08:50:40', NULL, NULL),
    (9, 9, 1, '상품에 하자가 있습니다. 실망이네요.', '2026-04-28 15:30:20', NULL, NULL),
    (10, 10, 4, '디자인이 화면과 같고 예뻐요.', '2026-04-29 11:00:00', NULL, NULL);