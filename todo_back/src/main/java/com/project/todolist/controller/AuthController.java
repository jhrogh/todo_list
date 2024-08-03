package com.project.todolist.controller;

import com.project.todolist.dto.JoinDto;
import com.project.todolist.dto.VerifyEmailDto;
import com.project.todolist.repository.MemberRepository;
import com.project.todolist.service.HashPassword;
import com.project.todolist.service.JoinMember;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;
    private final JoinMember joinMember;

    @GetMapping("/check-auth")      // 로그인 인증 여부
    public ResponseEntity<?> checkAuth() {
        Boolean isLoggedIn = null;

        if(isLoggedIn) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "User is authenticated");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Authentication required");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @GetMapping("/check-name")      // 로그인 사용자 이름
    public ResponseEntity<?> checkName() {
        Boolean isLoggedIn = null;

        if(isLoggedIn) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("memberId", "이름");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("memberId", " ");

            return ResponseEntity.ok().body(responseBody);
        }
    }

    @PostMapping("/login")      // 로그인하기
    public ResponseEntity<?> login() {
        Boolean isLoginSuccessful = null;

        if(isLoginSuccessful) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Login successful");
            responseBody.put("token", "jwt token 전달");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            // 비밀번호 불일치 (401)
//            Map<String, Object> responseBody = new HashMap<>();
//            responseBody.put("status", "error");
//            responseBody.put("message", "Invalid Id or password");
//
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);

            // 아이디 없음 (404)
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Not Found Id");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @PostMapping("/join/verify-email")      // 회원가입 - 이메일 인증
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailDto verifyEmailDto) {
        Boolean isVerifiedEmail = null;

        if(isVerifiedEmail) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Email verified successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 400 error
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Invalid verification token");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @PostMapping("/join")       // 회원가입 - 가입하기
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        // 1. 아이디 중복 판단
        Boolean isNewId = memberRepository.findByMemberId(joinDto.getMemberId()).isEmpty();     // T: 신규 / F: 기존
        // 2-1. 중복인 경우: 400 에러
        // 2-2. 중복 아닌 경우 아래 진행
        // 3. memberId, name, password, email -> JoinMember 테이블 저장

        if(isNewId) {  // 기존회원
            // 3. memberId, name, password, email -> JoinMember 테이블 저장
            joinMember.saveMember(joinDto);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Registration successful");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 400 error (아이디 중복)
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "MemberId already taken or email not verified");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
