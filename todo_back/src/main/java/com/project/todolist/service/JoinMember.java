package com.project.todolist.service;

import com.project.todolist.dto.JoinDto;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.MemberRepository;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinMember {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;

    public ResponseEntity<?> saveMember(JoinDto joinDto) {
        Boolean isNewId = memberRepository.findByMemberId(joinDto.getMemberId()).isEmpty();     // T: 신규 / F: 기존

        if(isNewId) {  // 신규 가입
            String memberId = joinDto.getMemberId();
            String name = joinDto.getName();
            String password;
            try {
                password = hashPassword.hashPassword(joinDto.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            String email = joinDto.getEmail();

            Member member = new Member();
            member.setMemberId(memberId);
            member.setName(name);
            member.setPassword(password);
            member.setEmail(email);

            memberRepository.save(member);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Signup successful");

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
