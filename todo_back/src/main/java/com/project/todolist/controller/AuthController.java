package com.project.todolist.controller;

import com.project.todolist.dto.JoinDto;
import com.project.todolist.dto.LoginDto;
import com.project.todolist.dto.VerifyEmailDto;
import com.project.todolist.service.CheckLogin;
import com.project.todolist.service.JoinMember;
import com.project.todolist.service.VerifyEmail;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final JoinMember joinMember;
    private final VerifyEmail verifyEmail;
    private final CheckLogin checkLogin;

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
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return checkLogin.checkMember(loginDto);
    }

    @PostMapping("/join/verify-email")      // 회원가입 - 이메일 인증
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailDto verifyEmailDto) {
        verifyEmail.saveEmail(verifyEmailDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Email verified successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/join/verify-email/code")
    public ResponseEntity<?> verifyEmailLink(@RequestParam("code") String code) {
        return verifyEmail.checkEmailToken(code);
    }

    @PostMapping("/join")       // 회원가입 - 가입하기
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        return joinMember.saveMember(joinDto);
    }
}
