# eCommerceBackOffice

## 📌 프로젝트 소개

이커머스 플랫폼을 운영하는 관리자를 위한 백오피스 시스템입니다.
관리자 계정 관리, 고객 정보 관리, 상품 관리, 주문 관리 기능을 제공하며
세션·쿠키 기반 인증을 통해 로그인 상태를 유지합니다.
 
---

## 🛠 기술 스택

| 분류 | 기술                          |
|------|-----------------------------|
| Language | Java 17                     |
| Framework | Spring Boot 4.0.5           |
| ORM | Spring Data JPA, Hibernate  |
| Database | MySQL 8.4.8                 |
| Build Tool | Gradle                      |
| 인증 | JWT / Token |
| 암호화 | BCrypt                      |
 
---

## 👥 팀원 소개

| 이름 | 담당 도메인 |
|------|------------|
| 김준형 | 고객 관리 시스템, 대쉬보드 |
| 김민혁 | 주문 정보 관리 시스템, 공통 응답, 전역 예외처리 |
| 윤영범 | 관리자 회원가입, 인증 및 로그인, 관리자 정보 관리, JWT 인증 |
| 정용태 | 상품 관리 시스템, 리뷰 정보 관리 |
 
---

## ⚙️ 개발 환경 설정

### 1. 사전 요구사항

- Java 17
- MySQL 8.4.8
- Gradle
### 2. DB 설정


```sql
CREATE DATABASE eCommerce;
```

### 3. application.properties 설정

`src/main/resources/application.properties` 

```properties
spring.application.name=eCommerceBackOffice
spring.datasource.url=jdbc:mysql://localhost:3306/eCommerce
spring.datasource.username=root
spring.datasource.password=12345678
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.servlet.session.timeout=24h
server.servlet.session.cookie.http-only=true
spring.sql.init.mode=never
spring.jpa.defer-datasource-initialization=true
spring.data.web.pageable.default-page-size=10
jwt.secret=team-six-scecret-key-test-password-12345678
jwt.expiration=86400000
```

---

## 📁 프로젝트 구조

```
src
└── main
    ├── java
    │   └── com.ecommercebackoffice
    │       ├── admin          # 관리자 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── entity
    │       │   ├── enums
    │       │   ├── repository
    │       │   └── service
    │       ├── auth           # 인증 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── jwt
    │       │   └── service
    │       ├── common         # 공통
    │       │   ├── entity     # BaseEntity
    │       │   ├── response   # 공통 응답
    │       │   └── exception  # 예외 처리
    │       ├── customer       # 고객 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── entity
    │       │   ├── repository
    │       │   └── service
    │       ├── dashboard        # 대쉬보드 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   └── service
    │       ├── order          # 주문 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── entity
    │       │   ├── repository
    │       │   └── service
    │       ├── product        # 상품 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── entity
    │       │   ├── repository
    │       │   └── service
    │       ├── review        # 상품 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── entity
    │       │   ├── repository
    │       │   └── service
    │       └── config         # 설정 (PasswordEncoder 등)
    └── resources
        ├── application.properties
        └── data.sql           # 더미 데이터
```
 
---

## 📏 팀 컨벤션

### 브랜치 네이밍
```
feature/도메인명
예시) feature/admin, feature/customer
```

### 커밋 메시지
```
<타입> : <제목>

<세부 구현 사항>
-
-
```

| 타입 | 설명 |
|------|------|
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| !HOTFIX | 급하게 치명적인 버그 수정 |
| Refactor | 프로덕션 코드 리팩토링 |
| Comment | 주석 추가 및 변경 |
| Docs | 문서 수정 |
| Rename | 파일/폴더명 수정 또는 이동 |
| Remove | 파일 삭제 |

### PR 규칙
- PR 시 최소 1명 승인 필요
### 코드 컨벤션
- DTO 네이밍: `Create(도메인)RequestDto` 형식
    - 예시) `CreateAdminRequestDto`, `UpdateCustomerRequestDto`
- 메서드에 Javadocs 주석 작성
- `return new` 방식으로 반환
### 삭제 전략
| 도메인 | 전략 |
|--------|------|
| 관리자 (Admin) | Soft Delete |
| 고객 (Customer) | Soft Delete |
| 주문 (Order) | Soft Delete |
| 상품 (Product) | Orphan Removal |

### 회의 시간
- 오전 10:10 / 저녁 19:30
- - -
## 🚀트러블슈팅

- - -
## 📊 ERD 

<img src="https://github.com/user-attachments/assets/d5925d23-c75b-482e-875e-7511cf1e029a" width="70%">

- - -
## 📋 API 명세서

<details>
<summary><strong>🔐 관리자 인증 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 관리자 회원가입 | POST | /admins| 201, 400, 409 |
| 로그인 | POST | /auth/login | 200, 400, 401, 403 |

<details> <summary><strong>관리자 회원가입</strong></summary>

### Request

- POST /admins/signup

```json
ex)
{
  "name": "홍길동",
  "email": "admin@test.com",
  "password": "password123",
  "phone": "010-1234-5678",
  "role": "운영 관리자"
}
```

### Validation
- name: 필수 입력
- email: 이메일 형식, 중복 불가
- password: 8자 이상
- phone: 010-XXXX-XXXX 형식
- role: 필수 선택

### Response

- 201 Created

```json
ex)
{
  "id": 1,
  "name": "홍길동",
  "email": "admin@test.com",
  "phone": "010-1234-5678",
  "role": "운영 관리자",
  "status": "승인대기",
  "createdAt": "2026-04-23T00:00:00"
}
```

### Error
- 400 Bad Request (필수값 누락, 형식 오류)
- 409 Conflict (중복 이메일)

</details>

<details> <summary><strong>로그인</strong></summary>

### Request

- POST /auth/login

```json
ex)
{
  "email": "admin@test.com",
  "password": "password123"
}
```

### Response

- 200 OK

```json
ex)
{
  "adminId": 1,
  "email": "admin@test.com",
  "role": "운영 관리자",
  "status": "활성"
}
```

### Behavior
- 세션 생성
- Set-Cookie: JSESSIONID=...

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (이메일 또는 비밀번호 불일치)
- 403 Forbidden (승인대기, 거부, 정지, 비활성 계정)

</details>
</details>

---

<details>
<summary><strong>👤 관리자 관리 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 관리자 목록 조회 | GET | /admins | 200, 401, 403 |
| 관리자 단건 조회 | GET | /admins/{adminId} | 200, 401, 403, 404 |
| 관리자 정보 수정 | PATCH | /admins/{adminId} | 200, 400, 401, 403, 404, 409 |
| 관리자 역할 변경 | PATCH | /admins/{adminId}/role | 200, 400, 401, 403, 404 |
| 관리자 상태 변경 | PATCH | /admins/{adminId}/status | 200, 400, 401, 403, 404 |
| 관리자 승인 | PATCH | /admins/{adminId}/approve | 200, 400, 401, 403, 404 |
| 관리자 거부 | PATCH | /admins/{adminId}/reject | 200, 400, 401, 403, 404 |
| 관리자 삭제 | DELETE | /admins/{adminId} | 204, 401, 403, 404 |
| 내 프로필 조회 | GET | /admins/me | 200, 401 |
| 내 프로필 수정 | PATCH | /admins/me | 200, 400, 401, 409 |
| 비밀번호 변경 | PATCH | /admins/me/password | 200, 400, 401 |

<details> <summary><strong>관리자 목록 조회</strong></summary>

### Request

- GET /admins?page=1&size=10&keyword=홍길동&sortBy=createdAt&direction=desc&role=운영 관리자&status=활성

### Response

- 200 OK

```
ex)
{
  "content": [
    {
      "id": 1,
      "name": "홍길동",
      "email": "admin@test.com",
      "phone": "010-1234-5678",
      "role": "운영 관리자",
      "status": "활성",
      "createdAt": "2026-04-23T00:00:00",
      "approvedAt": "2026-04-23T01:00:00"
    }
  ],
  "page": 1,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

### Error
- 401 Unauthorized (로그인 필요)
- 403 Forbidden(권한 없음)

</details>

<details> <summary><strong>관리자 단건 조회</strong></summary>

### Request

- GET /admins/{adminId}

### Response

- 200 OK

```
ex)
{
  "id": 1,
  "name": "홍길동",
  "email": "admin@test.com",
  "phone": "010-1234-5678",
  "role": "운영 관리자",
  "status": "활성",
  "createdAt": "2026-04-23T00:00:00",
  "approvedAt": "2026-04-23T01:00:00"
}
```

### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (관리자 없음)
- 403 Forbidden(권한 없음)

</details>

<details> <summary><strong>관리자 정보 수정</strong></summary>

### Request

- PATCH /admins/{adminId}

```
ex)
{
  "name": "김관리",
  "email": "manager@test.com",
  "phone": "010-9999-8888"
}
```

### Response

- 200 OK

```
ex)
{
  "id": 1,
  "name": "김관리",
  "email": "manager@test.com",
  "phone": "010-9999-8888",
  "updatedAt": "2026-04-23T02:00:00"
}
```

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (관리자 없음)
- 403 Forbidden(권한 없음)
- 409 Conflict (중복 이메일)

</details>

<details> <summary><strong>관리자 역활 변경</strong></summary>

### Request

- PATCH /admins/{adminId}/role


```
{
  "role": "OPERATION_ADMIN"
}

### Response

- 200 OK

{
ex)
  "id": 1,
  "role": "OPERATION_ADMIN",
  "updatedAt": "2026-04-23T02:30:00"
}

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 403 Forbidden(권한 없음)
- 404 Not Found (관리자 없음)
- 409 Conflict (중복 이메일)

</details>

<details> <summary><strong>관리자 상태 변경</strong></summary>

### Request

- PATCH /admins/{adminId}/status


```
{
"status": "ACTIVE"
}

### Response

- 200 OK

{
ex)
"id": 1,
"status": "ACTIVE",
"updatedAt": "2026-04-23T02:30:00"
}

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 403 Forbidden(권한 없음)
- 404 Not Found (관리자 없음)
- 409 Conflict (중복 이메일)

</details>

<details> <summary><strong>관리자 승인</strong></summary>

### Request

- PATCH /admins/{adminId}/approve

### Response

- 200 OK

```
ex)
{
  "id": 2,
  "status": "활성",
  "approvedAt": "2026-04-23T03:00:00"
}
```

### Error
- 400 Bad Request (승인대기 상태 아님)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (관리자 없음)

</details>

<details> <summary><strong>관리자 거부</strong></summary>

### Request

- PATCH /admins/{adminId}/reject

```
ex)
{
  "rejectReason": "관리자 승인 조건 미충족"
}
```

### Response

- 200 OK

```
ex)
{
  "id": 2,
  "status": "거부",
  "rejectedAt": "2026-04-23T03:10:00",
  "rejectReason": "관리자 승인 조건 미충족"
}
```

### Error
- 400 Bad Request (거부 사유 누락, 승인대기 상태 아님)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (관리자 없음)

</details>

<details> <summary><strong>관리자 삭제</strong></summary>

### Request

- DELETE /admins/{adminId}

### Response

- 204 NO CONTENT


### Error
- 401 Unauthorized (로그인 필요)
- 403 Forbidden(권한 없음)
- 404 Not Found (관리자 없음)

</details>

<details> <summary><strong>내 프로필 조회</strong></summary>

### Request

- GET /admins/me

### Response

- 200 OK

```
ex)
{
  "id": 1,
  "name": "홍길동",
  "email": "admin@test.com",
  "phone": "010-1234-5678"
}
```

### Error
- 401 Unauthorized (로그인 필요)

</details>

<details> <summary><strong>내 프로필 수정</strong></summary>

### Request

- PATCH /admins/me

```
ex)
{
  "name": "홍길동",
  "email": "admin@test.com",
  "phone": "010-1234-5678"
}
```

### Response

- 200 OK

```
ex)
{
  "id": 1,
  "name": "홍길동",
  "email": "admin@test.com",
  "phone": "010-1234-5678"
}
```

### Error
- 400 BAD REQUEST (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 409 CONFLICT(중복이메일)

</details>

<details> <summary><strong>비밀번호 변경</strong></summary>

### Request

- PATCH /admins/me/password

```
ex)
{
  "currentPassword": "password123",
  "newPassword": "newpassword123"
}
```

### Response

- 200 OK

```
ex)
{
  "message": "비밀번호가 변경되었습니다."
}
```

### Error
- 400 Bad Request (비밀번호 형식 오류)
- 401 Unauthorized (로그인 필요, 현재 비밀번호 불일치)

</details>
</details>

---


<details>
<summary><strong>👥 고객 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 고객 목록 조회 | GET | /customers | 200, 401 |
| 고객 단건 조회 | GET | /customers/{customerId} | 200, 401, 404 |
| 고객 수정 | PATCH | /customers/{customerId} | 200, 400, 401, 404, 409 |
| 고객 상태 변경 | PATCH | /customers/{customerId}/status | 200, 400, 401, 404 |
| 고객 삭제 | DELETE | /customers/{customerId} | 204, 401, 404 |

<details> <summary><strong>고객 목록 조회</strong></summary>

### Request

- GET /customers

### Response

- 200 OK

```json
ex)
{
  "content": [
    {
          "id": 2,
          "name": "이비활성",
          "email": "inactive@test.com",
          "phoneNumber": "010-3333-4444",
          "customerStatus": "INACTIVE",
          "createdAt": "2026-04-28T19:41:38",
          "modifiedAt": "2026-04-28T19:41:38",
          "totalOrderCount": 0,
          "totalOrderAmount": 0
    }
  ],
  "pageNumber": 1,
  "pageSize": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

### Error
- 401 Unauthorized (로그인 필요)

</details>

<details> <summary><strong>고객 단건 조회</strong></summary>

### Request

- GET /customers/{customerId}

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "name": "김고객",
  "email": "customer@test.com",
  "phone": "010-2222-3333",
  "customerStatus": "활성",
  "createdAt": "2026-04-23T00:00:00"
  "modifiedAt": "2026-04-28T19:41:38",
  "totalOrderCount": 0,
  "totalOrderAmount": 0
}
```

### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (고객 없음)

</details>

<details> <summary><strong>고객 수정</strong></summary>

### Request

- PATCH /customers/{customerId}

```json
ex)
{
  "name": "김수정",
  "email": "edit@test.com",
  "phone": "010-5555-6666"
}
```

### Response

- 200 OK

```json
ex)
{
  "name": "김수정",
  "email": "edit@test.com",
  "phone": "010-5555-6666",
  "modifiedAt": "2026-04-23T04:00:00"
}
```

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (고객 없음)
- 409 Conflict (중복 이메일)

</details>

<details> <summary><strong>고객 상태 변경</strong></summary>

### Request

- PATCH /customers/{customerId}/status

```json
ex)
{
  "status": "김수정"
}
```

### Response

- 200 OK

```json
ex)
{
  "status": "김수정"
}
```

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (고객 없음)
- 409 Conflict (중복 이메일)

</details>

<details> <summary><strong>고객 삭제</strong></summary>

### Request

- DELETE /customers/{customerId}

### Response

- 204 No Content

### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (고객 없음)

</details>
</details>

---

<details>
<summary><strong>🛒 상품 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 상품 등록 | POST | /products | 201, 400, 401 |
| 상품 목록 조회 | GET | /products | 200, 401 |
| 상품 단건 조회 | GET | /products/{productId} | 200, 401, 404 |
| 상품 수정 | PATCH | /products/{productId} | 200, 400, 401, 404 |
| 상품 재고 변경 | PATCH | /products/{productId}/stock | 200, 400, 401, 404 |
| 상품 상태 변경 | PATCH | /products/{productId}/status | 200, 400, 401, 404 |
| 상품 삭제 | DELETE | /products/{productId} | 204, 401, 404 |

<details> <summary><strong>상품 등록</strong></summary>

### Request

- POST /products

```json
ex)
{
  "name": "노트북",
  "category": "전자기기",
  "price": 1500000,
  "stock": 10,
  "status": "ON_SALE"
}
```

### Response

- 201 Created

```json
ex)
{
  "id": 1,
  "name": "노트북",
  "category": "전자기기",
  "price": 1500000,
  "stock": 10,
  "status": "판매중",
  "createdAt": "2026-04-23T00:00:00",
  "createdBy": "홍길동"
}
```

### Error
- 400 Bad Request (필수값 누락, 형식 오류)
- 401 Unauthorized (로그인 필요)

</details>

<details> <summary><strong>상품 목록 조회</strong></summary>

### Request

- GET /products?page=1&size=10&keyword=노트북&sortBy=price&direction=asc&category=ELECTRONICS&status=ON_SALE

### Response

- 200 OK

```json
ex)
{
  "content": [
    {
      "id": 1,
      "name": "노트북",
      "category": "ELECTRONICS",
      "price": 1500000,
      "stock": 10,
      "status": "ON_SALE",
      "createdAt": "2026-04-23T00:00:00",
      "createdBy": "홍길동"
    }
  ],
  "page": 1,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

</details>

<details> <summary><strong>상품 단건 조회</strong></summary>

### Request

- GET /products/{productId}

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "name": "노트북",
  "category": "전자기기",
  "price": 1500000,
  "stock": 10,
  "status": "ON_SALE",
  "createdAt": "2026-04-23T00:00:00",
  "adminName": "홍길동",
  "adminEmail": "admin@test.com"
  "latestReview": [
            {
                "customerName": "Kim",
                "rating": 4,
                "content": "디자인이 화면과 같고 예뻐요.",
                "createdAt": "2026-04-29T11:00:00"
            },
            {
                "customerName": "Kim",
                "rating": 5,
                "content": "재구매 의사 100% 입니다.",
                "createdAt": "2026-04-28T08:50:40"
            },
            {
                "customerName": "Kim",
                "rating": 2,
                "content": "배송이 너무 늦어서 아쉬웠어요.",
                "createdAt": "2026-04-27T12:05:30"
            }
        ],
  "reviewStatistics": {
            "ratingAverage": 4.3,
            "totalReview": 6,
            "fiveRatings": 4,
            "fourRatings": 1,
            "threeRatings": 0,
            "twoRatings": 1,
            "oneRatings": 0
        }
}
```

### Error
- 404 Not Found (상품 없음)

</details>

<details> <summary><strong>상품 수정</strong></summary>

### Request

- PATCH /products/{productId}

```json
ex)
{
  "name": "노트북"
  "category": "전자기기"
  "price": 1500000
}
```

### Response

- 200 OK

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (상품 없음)

</details>

<details> <summary><strong>상품 재고 변경</strong></summary>

### Request

- PATCH /products/{productId}/stock

```json
ex)
{
  "stock": 0
}
```

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "stock": 0,
  "status": "OUT_OF_STOCK",
  "updatedAt": "2026-04-23T05:00:00"
}
```

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (상품 없음)

</details>
<details> <summary><strong>상품 상태 변경</strong></summary>

### Request

- PATCH /products/{productId}/status

```json
ex)
{
  "status": "OUT_OF_STOCK"
}
```

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "stock": 0,
  "status": "OUT_OF_STOCK",
  "updatedAt": "2026-04-23T05:00:00"
}
```

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (상품 없음)

</details>
<details> <summary><strong>상품 삭제</strong></summary>

### Request

- DELETE/products/{productId}


### Response

- 204 NO_CONTENT


### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (상품 없음)

</details>
</details>

---

<details>
<summary><strong>📦 주문 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 주문 생성 | POST | /orders | 201, 400, 401, 404, 409 |
| 주문 목록 조회 | GET | /orders | 200, 401 |
| 주문 단건 조회 | GET | /orders/{orderId} | 200, 401, 404 |
| 주문 상태 변경 | PATCH | /orders/{orderId}/status | 200, 400, 401, 404 |
| 주문 취소 | PATCH | /orders/{orderId}/cancel | 200, 400, 401, 404, 409 |

<details> <summary><strong>주문 생성</strong></summary>

### Request

- POST /orders

```json
ex)
{
  "customerId": 1,
  "productId": 1,
  "quantity": 2
}
```

### Response

- 201 Created

```json
ex)
{
  "id": 1,
  "orderNumber": "ORD-20260423-0001",
  "customerName": "김고객",
  "productName": "노트북",
  "quantity": 2,
  "totalPrice": 3000000,
  "status": "준비중",
  "createdAt": "2026-04-23T06:00:00",
  "createdBy": "홍길동"
}
```

### Error
- 400 Bad Request (수량 1 미만)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (고객 또는 상품 없음)
- 409 Conflict (재고 부족, 품절 상품, 단종 상품)

</details>

<details> <summary><strong>주문 목록 조회</strong></summary>

### Request

- GET /orders?page=1&size=10&keyword=ORD-20260423-0001&sortBy=createdAt&direction=DESC&status=READY

### Response

- 200 OK

```json
ex)
{
  "content": [
    {
      "id": 1,
      "orderNumber": "ORD-20260423-0001",
      "customerName": "김고객",
      "productName": "노트북",
      "quantity": 2,
      "totalPrice": 3000000,
      "status": "READY",
      "createdAt": "2026-04-23T06:00:00",
      "createdBy": "홍길동"
    }
  ],
  "page": 1,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

</details>

<details> <summary><strong>주문 단건 조회</strong></summary>

### Request

- GET /orders/{orderId}

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "orderNumber": "ORD-20260423-0001",
  "customerName": "김고객",
  "customerEmail": "customer@test.com",
  "productName": "노트북",
  "quantity": 2,
  "totalPrice": 3000000,
  "status": "READY",
  "createdAt": "2026-04-23T06:00:00",
  "createdByName": "홍길동",
  "createdByEmail": "admin@test.com",
  "createdByRole": "CS_ADMIN"
}
```

### Error
- 404 Not Found (주문 없음)

</details>

<details> <summary><strong>주문 상태 변경</strong></summary>

### Request

- PATCH /orders/{orderId}
```json
ex)
{
  "status": "DELIVERY"
}
```

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "orderNumber": "ORD-20260423-0001",
  "status": "DELIVERY",
  "updatedAt": "2026-04-23T07:00:00"
}
```

### Error
- 400 Bad Request (허용되지 않은 상태 변경)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (주문 없음)

</details>

<details> <summary><strong>주문 취소</strong></summary>

### Request

- PATCH /orders/{orderId}/cancel

```json
ex)
{
  "cancelReason": "고객 요청 취소"
}
```

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "orderNumber": "ORD-20260423-0001",
  "status": "CANCEL",
  "cancelReason": "고객 요청 취소",
  "modifiedAt": "2026-04-23T07:10:00"
}
```

### Error
- 400 Bad Request (취소 사유 누락)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (주문 없음)
- 409 Conflict (준비중 상태가 아니어서 취소 불가)

</details>
</details>

---

<details>
<summary><strong>⭐ 리뷰 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 리뷰 목록 조회 | GET | /reviews | 200, 401 |
| 리뷰 단건 조회 | GET | /reviews/{reviewId} | 200, 401, 404 |
| 리뷰 삭제 | DELETE | /reviews/{reviewId} | 204, 401, 404 |

<details> <summary><strong>리뷰 목록 조회</strong></summary>

### Request

- GET /reviews?page=1&size=10&keyword=노트북&sortBy=createdAt&direction=desc&rating=5

### Response

- 200 OK

```json
ex)
{
  "content": [
    {
      "id": 1,
      "orderNumber": "ORD-20260423-0001",
      "customerName": "김고객",
      "productName": "노트북",
      "rating": 5,
      "content": "배송이 빨랐어요.",
      "createdAt": "2026-04-23T08:00:00"
    }
  ],
  "page": 1,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

</details>

<details> <summary><strong>리뷰 단건 조회</strong></summary>

### Request

- GET /reviews/{reviewId}

### Response

- 200 OK

```json
ex)
{
  "id": 1,
  "productName": "노트북",
  "customerName": "김고객",
  "customerEmail": "customer@test.com",
  "rating": 5,
  "content": "배송이 빨랐어요.",
  "createdAt": "2026-04-23T08:00:00"
}
```

### Error
- 404 Not Found (리뷰 없음)

</details>

<details> <summary><strong>리뷰 삭제</strong></summary>

### Request

- DELETE /reviews/{reviewId}

### Response

- 204 No Content

### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (리뷰 없음)

</details>
</details>

---

<details>
<summary><strong>📊 대시보드 API</strong></summary>

| 기능 | Method | URL | 상태코드 |
|------|--------|-----|----------|
| 대시보드 조회 | GET | /dashboard | 200, 401 |

<details>
<summary><strong>대시보드 조회</strong></summary>

### Request

- GET /dashboard

### Response

- 200 OK
```json
{
  "summaryDto": {
    "totalAdmin": 1,
    "activeAdmin": 1,
    "totalCustomers": 3,
    "activeCustomers": 1,
    "totalProducts": 1,
    "lowStockProducts": 0,
    "totalOrders": 2,
    "todayOrders": 0,
    "totalReview": 0,
    "reviewAverage": 0.0
  },
  "widgetsDto": {
    "totalRevenue": 4500000,
    "todayTotalRevenue": 0,
    "preparingOrders": 2,
    "deliveryOrders": 0,
    "completeOrders": 0,
    "lowStockProduct": 0,
    "lessStockProduct": 0
  },
  "chartsDto": {
    "reviewRatingDistribution": [],
    "customerStatusDistribution": [
      { "customerStatus": "ACTIVE", "count": 1 },
      { "customerStatus": "INACTIVE", "count": 1 },
      { "customerStatus": "SUSPENDED", "count": 1 }
    ],
    "categoryDistribution": [
      { "productCategory": "ELECTRONICS", "count": 1 }
    ]
  },
  "recentDto": [
    {
      "orderNumber": "ORD-20260427-0001",
      "customerName": "김활성",
      "productName": "노트북",
      "totalPrice": 3000000,
      "status": "READY"
    }
  ]
}
```

### Error
- 401 Unauthorized (로그인 필요)

</details>
</details>
  
  
