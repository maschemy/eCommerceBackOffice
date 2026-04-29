package com.ecommercebackoffice.auth.jwt;

import com.ecommercebackoffice.admin.enums.AdminRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component // Spring Bean 등록 (다른 곳에서 주입 가능)
public class JwtProvider {

    private final long expirationTime;  // 24시간
    private final SecretKey key;// 지정된 키와 알고리즘으로 위조방지 서명

    //생성자
    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationTime
    ) {
        this.key = Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8) // secretKey 문자열 → JWT 서명용 SecretKey 객체 변환
        );
        this.expirationTime = expirationTime;
    }

    public String createToken(Long adminId , String email , AdminRole role) //토큰생성
    {
        Date now = new Date(); // 현재시간
        Date accessTokenExpiresIn = new Date(now.getTime() + expirationTime);//로그인 시간 + 만료시간 24시간

        //Header.Payload.Signature
        return Jwts.builder()//토큰 생성 시작
                .subject(String.valueOf(adminId)) //토큰의주인 = adminId
                .claim("email",email)//추가사용자정보 email
                .claim("role",role.name())//추가사용자정보 role
                .issuedAt(now)//토큰 발급시간 iat
                .expiration(accessTokenExpiresIn)//토큰 만료시간 exp
                .signWith(key)//JWT 위조 방지 서명
                .compact(); // 최종 JWT 문자열 생성
    }
    // JWT에서 관리자 ID 추출
    public Long getAdminId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    // 이메일 추출
    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    //역활 추출
    public AdminRole getRole(String token) {
        String role = getClaims(token).get("role", String.class);
        return AdminRole.valueOf(role); // String → Enum 변환
    }

    //JWT Payload(Claims) 파싱
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key) //서명 검증 (토큰이 맞는지 확인)
                .build()//parser 생성
                .parseSignedClaims(token)//토큰 해석 , 서명 ,만료시간 검증
                .getPayload();//Payload 반환
    }

    //유효성검증
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true; //정상 토큰 일시 반환
        } catch (JwtException | IllegalArgumentException e) { // JwtExpection: 만료/위조/잘못된형식
            return false;
        }
    }

}
