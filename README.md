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
| 인증 | Session / Cookie (HttpOnly) |
| 암호화 | BCrypt                      |
 
---

## 👥 팀원 소개

| 이름 | 담당 도메인 |
|------|------------|
| 김준형 | 고객 관리 시스템 |
| 김민혁 | 주문 정보 관리 시스템 |
| 윤영범 | 관리자 회원가입, 인증, 정보 관리 |
| 정용태 | 상품 관리 시스템 |
 
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
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.servlet.session.timeout=24h
server.servlet.session.cookie.http-only=true
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.data.web.pageable.default-page-size=10
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
    │       │   └── service
    │       ├── customer       # 고객 도메인
    │       │   ├── controller
    │       │   ├── dto
    │       │   ├── entity
    │       │   ├── repository
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
    │       ├── common         # 공통
    │       │   ├── entity     # BaseEntity
    │       │   └── exception  # 예외 처리
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
- 오전 / 저녁 19:30
