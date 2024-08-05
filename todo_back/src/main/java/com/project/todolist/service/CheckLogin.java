package com.project.todolist.service;

import com.project.todolist.dto.LoginDto;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.MemberRepository;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckLogin {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;

    public ResponseEntity<?> checkMember(LoginDto loginDto) {
        // 아이디로 디비 조회
        String memberId = loginDto.getMemberId();
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String password;
            try {
                password = hashPassword.hashPassword(loginDto.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Password hashing failed", e);
            }
            if(password.equals(member.getPassword())) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("message", "Login successful");

                return ResponseEntity.ok().body(responseBody);
            }
            else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "error");
                responseBody.put("message", "Failed Pw");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
            }
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Not Found Id");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }
}
