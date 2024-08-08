package com.project.todolist.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtToken {
    @Value("${jwt.secret}")
    private String secretKeyBase64;

    private Key key;
    private long expirationTime = 3600000; // 1시간

    @PostConstruct
    public void init() {
        // Base64 디코딩하여 키 객체 생성
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyBase64);
        key = Keys.hmacShaKeyFor(decodedKey);
    }

    // 토큰 생성
    public String createToken(String memberId) {
        return Jwts.builder()
                .setSubject(memberId)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 유효성 체크
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);  // 만료된 토큰 false
    }

    // 토큰에서 사용자 정보(아이디) 추출
    public String findMemberId(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    // 토큰 쿠키 저장
    public void saveToken(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(6))
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    // 쿠키에서 토큰 삭제
    public void deleteToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/"); // 쿠키가 유효할 경로 설정
        cookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
        response.addCookie(cookie);
    }

    // 쿠키에서 토큰 추출
    public String findToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "token".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

}
