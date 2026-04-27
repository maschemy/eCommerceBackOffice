INSERT INTO customers (name, email, phone_number, status, created_at, modified_at, deleted_at)
VALUES
    ('김활성', 'active@test.com', '010-1111-2222', 'ACTIVE', NOW(), NOW(), NULL),
    ('이비활성', 'inactive@test.com', '010-3333-4444', 'INACTIVE', NOW(), NOW(), NULL),
    ('박정지', 'suspended@test.com', '010-5555-6666', 'SUSPENDED', NOW(), NOW(), NULL),
    ('최탈퇴', 'deleted@test.com', '010-7777-8888', 'ACTIVE', NOW(), NOW(), NOW());