package com.project.todolist.controller;

import com.project.todolist.dto.CheckMemberDto;
import com.project.todolist.dto.JoinDto;
import com.project.todolist.dto.LoginDto;
import com.project.todolist.dto.VerifyEmailDto;
import com.project.todolist.service.CheckLogin;
import com.project.todolist.service.JoinMember;
import com.project.todolist.service.JwtToken;
import com.project.todolist.service.Mypage;
import com.project.todolist.service.VerifyEmail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtToken jwtToken;
    private final Mypage mypage;

    @GetMapping("/check-auth")      // 로그인 인증 여부 (jwt 토큰)
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        String token = jwtToken.findToken(request);

        if(token != null) {
            boolean isValidateToken = jwtToken.validateToken(token);        // false: 만료됨

            if(isValidateToken) {
                String name = mypage.findUserName(request);
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("name", name);

                return ResponseEntity.ok().body(responseBody);
            }
            else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "Token is not Validate");

                return ResponseEntity.ok().body(responseBody);
            }
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "server error");
            responseBody.put("message", "Authentication required");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return checkLogin.logoutUser(request, response);
    }

    @PostMapping("/login")      // 로그인하기
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        CheckMemberDto result = checkLogin.checkMember(loginDto);

        if(result.isSuccess()) {
            String token = jwtToken.createToken(result.getMemberId());
            jwtToken.saveToken(response, token);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", result.getMessage());

            return ResponseEntity.ok().body(responseBody);
        } else{
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", result.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @GetMapping("/join/memberId/unique")
    public ResponseEntity<?> uniqueMemberId(@RequestParam("memberId") String memberId) {
        return joinMember.checkMemberId(memberId);
    }

    @PostMapping("/join/verify-email")      // 회원가입 - 이메일 인증
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailDto verifyEmailDto) {
        verifyEmail.sendEmailCode(verifyEmailDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Email verified successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/join/verify-email/code")
    public ResponseEntity<?> verifyEmailCode(@RequestParam("code") String code) {
        return verifyEmail.checkEmailToken(code);
    }

    @PostMapping("/join")       // 회원가입 - 가입하기
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        return joinMember.saveMember(joinDto);
    }
}
