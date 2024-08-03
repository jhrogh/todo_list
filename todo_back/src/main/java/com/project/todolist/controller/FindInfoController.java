package com.project.todolist.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/find")
public class FindInfoController {
    @PostMapping("/id")
    public ResponseEntity<?> findId() {
        Boolean isFindId = null;

        if(isFindId) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("memberId", "찾은 아이디");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 404 error
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "No matching");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @PostMapping("/pw/verify-email")
    public ResponseEntity<?> pwVerifyEmail() {
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
            responseBody.put("message", "Invalid email or memberId");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @PostMapping("/changepw")
    public ResponseEntity<?> changePw() {
        Boolean isChangePw = null;

        if(isChangePw) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Password successfully change");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 400 error
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Password reset token is invalid or expired");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
