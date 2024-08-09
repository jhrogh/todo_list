package com.project.todolist.service;

import com.project.todolist.dto.JoinDto;
import com.project.todolist.entity.EmailVerification;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.EmailVerificationRepository;
import com.project.todolist.repository.MemberRepository;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinMember {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;
    private final EmailVerificationRepository emailVerificationRepository;

    public ResponseEntity<?> saveMember(JoinDto joinDto) {
        String verificationCode = joinDto.getCode();
        Optional<EmailVerification> emailVerificationOpt = emailVerificationRepository.findByVerificationCode(verificationCode);

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
            member.setEmailVerified(true);
            member.setCreateAt(LocalDateTime.now());

            memberRepository.save(member);

            EmailVerification emailVerification = emailVerificationOpt.get();
            // EmailVerification과의 관계 설정
            emailVerification.setMember(member);
            emailVerificationRepository.save(emailVerification);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Signup successful");

            return ResponseEntity.ok().body(responseBody);
        }

    public ResponseEntity<?> checkMemberId(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        Map<String, Object> responseBody = new HashMap<>();
        if (optionalMember.isEmpty()) {
            responseBody.put("status", "success");
            responseBody.put("message", "MemberId is unique");

            return ResponseEntity.ok().body(responseBody);
        } else {
            responseBody.put("status", "failed");
            responseBody.put("message", "MemberId already taken");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
