-- 1. admins 먼저 (슈퍼 관리자)
INSERT INTO admins (name, email, password, phone_number, role, status, created_at, modified_at)
VALUES ('테스트관리자', 'admin@test.com', '$2a$12$7FxxCUpdz2j/Sg1Y02xl6uj1PR6S9LFLYshQTIvdJN8JrQS92b0O2', '010-0000-0000', 'SUPER_ADMIN', 'ACTIVE', NOW(), NOW());
-- 2. customers
INSERT INTO customers (name, email, phone_number, status, created_at, modified_at, deleted_at)
VALUES
    ('김활성', 'active@test.com', '010-1111-2222', 'ACTIVE', NOW(), NOW(), NULL),
    ('이비활성', 'inactive@test.com', '010-3333-4444', 'INACTIVE', NOW(), NOW(), NULL),
    ('박정지', 'suspended@test.com', '010-5555-6666', 'SUSPENDED', NOW(), NOW(), NULL),
    ('최탈퇴', 'deleted@test.com', '010-7777-8888', 'ACTIVE', NOW(), NOW(), NOW());

-- 3. products (admin_id 참조)
INSERT INTO products (name, category, price, stock, status, admin_id, created_at, modified_at)
VALUES ('노트북', 'ELECTRONICS', 1500000, 10, 'ON_SALE', 1, NOW(), NOW());

-- 4. orders (admin_id, customer_id, product_id 모두 참조)
INSERT INTO orders (order_number, quantity, total_price, status, admin_id, customer_id, product_id, created_at, modified_at)
VALUES
    ('ORD-20260427-0001', 2, 3000000, 'READY', 1, 1, 1, NOW(), NOW()),
    ('ORD-20260427-0002', 1, 1500000, 'READY', 1, 1, 1, NOW(), NOW());
