package com.project.todolist.service;

import com.project.todolist.dto.ChangePwDto;
import com.project.todolist.dto.FindIdDto;
import com.project.todolist.dto.FindPwDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindInfo {
    private final MemberRepository memberRepository;
    private final CreateEmailCode createEmailCode;
    private final EmailVerificationRepository emailVerificationRepository;
    private final HashPassword hashPassword;

    public ResponseEntity<?> searchId(FindIdDto findIdDto) {
        String name = findIdDto.getName();
        Optional<Member> optionalMember = memberRepository.findByName(name);

        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getEmail().equals(findIdDto.getEmail())) {
                String memberId = member.getMemberId();

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("memberId", memberId);

                return ResponseEntity.ok().body(responseBody);
            } else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "No matching Email");

                return ResponseEntity.badRequest().body(responseBody);
            }
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "Not Found");
            responseBody.put("message", "No matching");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    public ResponseEntity<?> searchPwEmailVerification(FindPwDto findPwDto) {
        // 이메일, 아이디 전달 받음 (이메일 공통)
        String memberId = findPwDto.getMemberId();
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        if(optionalMember.isPresent()) {
            String verificationCode = createEmailCode.emailCode(findPwDto.getEmail());

            LocalDateTime createAt = LocalDateTime.now();
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(3); // 인증 코드는 3분 동안 유효

            EmailVerification emailVerification = optionalMember.get().getEmailVerification();
            emailVerification.setVerificationCode(verificationCode);
            emailVerification.setCreateAt(createAt);
            emailVerification.setExpiresAt(expiresAt);
            emailVerificationRepository.save(emailVerification);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Email verified successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "No matching");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    public ResponseEntity<?> changePassword(ChangePwDto changePwDto) {
        String memberId = changePwDto.getMemberId();
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();
        String newHashPassword;
        try {
            newHashPassword = hashPassword.hashPassword(changePwDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        member.setPassword(newHashPassword);
        memberRepository.save(member);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Password successfully change");

        return ResponseEntity.ok().body(responseBody);
    }
}
