package com.project.todolist.controller;

import com.project.todolist.service.JwtToken;
import com.project.todolist.service.Mypage;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(HttpServletRequest request, @RequestBody Map<String, String> password) {
        return mypage.deleteAccount(request, password);
    }
}
