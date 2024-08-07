package com.project.todolist.controller;

import com.project.todolist.service.JwtToken;
import com.project.todolist.service.Mypage;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {
    private final Mypage mypage;
    private final JwtToken jwtToken;

    @GetMapping("/show")
    public ResponseEntity<?> show(HttpServletRequest request) {
        return mypage.showMyInfo(request);
    }

    @PostMapping("/verify-email")
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
            responseBody.put("message", "Email verification failed");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @PostMapping("/delete-account")
    public ResponseEntity<?> deleteAccount() {
        Boolean isDeleteAccount = null;

        if(isDeleteAccount) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Account deleted successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 400 error 비밀번호 불일치
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Invalid password");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
