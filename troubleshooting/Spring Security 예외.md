# 🛠️ Trouble Shooting: Spring Security 예외의 GlobalExceptionHandler 위임

---

## 📌 배경 (Background)
* **상황:** 프로젝트 내에서 `GlobalExceptionHandler`를 통해 공통 예외 처리를 구현함.
* **이슈:** `Spring Security` 예외는 컨트롤러(Controller)에 도달하기 전 `Filter` 단계에서 발생하므로, `@RestControllerAdvice`가 이를 감지하지 못함.
* **목표:** Security Filter 단에서 발생하는 예외를 `HandlerExceptionResolver`를 통해 `GlobalExceptionHandler`로 위임하여 일관된 예외 처리를 수행하고자 함.

---

## 🧐 Spring Security의 예외 처리 아키텍처

1. **FilterChain:** 요청을 처리하며, 예외 발생 시 예외 처리 필터가 작동.
2. **ExceptionTranslationFilter:** 예외를 캐치하여 Spring Security 예외로 변환 후 `AuthenticationEntryPoint` 또는 `AccessDeniedHandler`를 호출.
3. **AuthenticationEntryPoint (인증 에러):** 인증되지 않은 요청 처리 (예: 401 Unauthorized).
4. **AccessDeniedHandler (인가 에러):** 권한이 부족한 요청 처리 (예: 403 Forbidden).

---

## 💡 해결 방안: HandlerExceptionResolver 활용
`HandlerExceptionResolver`는 Spring MVC 영역의 컴포넌트로, 컨트롤러 밖에서 발생한 예외를 Spring MVC의 예외 처리 체인으로 위임합니다.

* **구현 핵심:** 커스텀 `AuthenticationEntryPoint`와 `AccessDeniedHandler`에서 `HandlerExceptionResolver`를 호출하여 예외를 위임.
* **코드 예시:**
    ```java
    resolver.resolveException(
            request,
            response,
            null, // Security Filter 단계이므로 handler 정보 없음
            new UnauthorizedException("인증이 필요합니다.")
    );
    ```
    * `handler`가 `null`인 경우, Spring MVC 예외 처리 체인이 다음 `ExceptionResolver`를 순차적으로 탐색하며 `@ExceptionHandler`를 찾아 실행합니다.

---

## ⚠️ 발생한 문제 및 해결 (Issue & Fix)

### 1. Bean 의존성 주입 에러
* **현상:** `HandlerExceptionResolver` 타입의 Bean이 여러 개 존재하여 `Could not autowire` 에러 발생.
* **원인:** Spring MVC 내부에 기본적으로 여러 `HandlerExceptionResolver` 구현체가 존재하기 때문.
* **해결:** `@Qualifier("handlerExceptionResolver")`를 사용하여 Spring MVC 기본 예외 처리 Resolver를 명확히 지정.

### 2. Lombok과 @Qualifier의 충돌
* **현상:** `@RequiredArgsConstructor` 사용 시 세부적인 Bean 설정이 반영되지 않음.
* **해결:** Lombok 자동 생성자를 제거하고, 수동으로 생성자를 작성하여 `@Qualifier`를 명시적으로 적용.

### 📝 수정된 코드 (예시)
```java
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver resolver;

    // 수동 생성자로 @Qualifier 명시
    public JwtAccessDeniedHandler(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        resolver.resolveException(
                request,
                response,
                null,
                new AdminPermissionException("접근 권한이 없습니다.")
        );
    }
}