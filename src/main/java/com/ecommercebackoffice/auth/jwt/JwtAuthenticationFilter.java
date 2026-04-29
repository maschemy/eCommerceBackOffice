package com.ecommercebackoffice.auth.jwt;

import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component// Spring Bean 등록
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws IOException, ServletException {

        String authorizationHeader = request.getHeader("Authorization");
        //토큰이 있는 헤더 가져오기

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
        {
            chain.doFilter(request, response);
            return;
        }
        // 토큰이 없거나,
        // Bearer 형식이 아니면 JWT 인증 처리 없이 다음 필터로 넘김
        String token = authorizationHeader.substring(7);//bearer 공백까지 7글자뒤에 토큰

        if(!jwtProvider.validateToken(token)){
            chain.doFilter(request,response);
            return;
        }

        Long adminId = jwtProvider.getAdminId(token);
        String email = jwtProvider.getEmail(token);
        AdminRole role = jwtProvider.getRole(token);

        /*스프링이 제공하는
        현재 사용자가 누구고,
                어떤 권한을 가졌는지
        Spring Security가 이해할 수 있게 담는 표준 인증 객체*/
        UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(adminId, email, role);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 현재 요청 사용자 정보를 SecurityContext에 저장

        chain.doFilter(request,response);

    }

    private static @NonNull UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(Long adminId, String email, AdminRole role) {
        LoginAdmin loginAdmin = new LoginAdmin(adminId, email, role);

        /* Spring Security 권한 객체 생성
         hasRole("SUPER_ADMIN")
         → 내부적으로 ROLE_SUPER_ADMIN 형태 사용 */
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );

        /* Spring Security 인증 객체 생성
         principal: 로그인 사용자 정보
         credentials: 비밀번호 (JWT 인증 완료 상태라 null)
        authorities: 권한 정보 */
        return new UsernamePasswordAuthenticationToken(
                loginAdmin,
                null,
                authorities
        );
    }
}
