package com.project.todolist.service;

import com.project.todolist.entity.Member;
import com.project.todolist.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class Mypage {
    private final JwtToken jwtToken;
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;
    private final CheckLogin checkLogin;

    public ResponseEntity<?> showMyInfo(HttpServletRequest request) {
        String token = jwtToken.findToken(request);
        String memberId = jwtToken.findMemberId(token);

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Map<String, Object> user = new HashMap<>();
        user.put("memberId", member.getMemberId());
        user.put("name", member.getName());
        user.put("email", member.getEmail());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("user", user);

        return ResponseEntity.ok().body(responseBody);
    }

    public String findUserName(HttpServletRequest request) {
        String token = jwtToken.findToken(request);
        String memberId = jwtToken.findMemberId(token);

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        return member.getName();
    }

    public ResponseEntity<?> deleteAccount(HttpServletRequest request, @RequestBody Map<String, String> password, HttpServletResponse response) {
        String hashpassword;
        try {
            hashpassword = hashPassword.hashPassword(password.get("password"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            if (hashpassword.equals(member.getPassword())) {
                checkLogin.logoutUser(request, response);
                memberRepository.delete(member);
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("message", "Account deleted successfully");

                return ResponseEntity.ok().body(responseBody);
            } else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "Failed password");

                return ResponseEntity.badRequest().body(responseBody);
            }
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Invalid password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }
}
