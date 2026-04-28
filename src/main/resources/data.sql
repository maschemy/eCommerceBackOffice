INSERT INTO admins (name, email, password, phone_number, role, status, created_at, modified_at)
VALUES ('테스트관리자',
        'admin@test.com',
        '$2a$04$8Nnn8w7qFb2Y6ADgO2N6zuvmg03s2kimiXDiMU4RjpXwKRZ/f8Zyq',
        '010-0000-0000',
        'SUPER_ADMIN', 'ACTIVE', NOW(), NOW());