package com.project.todolist.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    @GetMapping("/check-auth")
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

    @GetMapping("/check-name")
    public ResponseEntity<?> checkName() {
        Boolean isLoggedIn = null;

        if(isLoggedIn) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("userId", "이름");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("userId", " ");

            return ResponseEntity.ok().body(responseBody);
        }
    }

    @PostMapping("/login")
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

    @PostMapping("/join/verify-email")
    public ResponseEntity<?> verifyEmail() {
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

    @PostMapping("/join")
    public ResponseEntity<?> join() {
        Boolean isSignup = null;

        if(isSignup) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Registration successful");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 400 error
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "UserId already taken or email not verified");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
