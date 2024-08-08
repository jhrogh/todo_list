package com.project.todolist.service;

import com.project.todolist.dto.VerifyEmailDto;
import com.project.todolist.entity.EmailVerification;
import com.project.todolist.repository.EmailVerificationRepository;
import com.project.todolist.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmail {
    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;
    private final CreateEmailCode createEmailCode;

    public void sendEmailCode(VerifyEmailDto verifyEmailDto) {
        // 1. 이메일 저장
        String email = verifyEmailDto.getEmail();
        String verificationCode = createEmailCode.emailCode(email);
        // 인증 코드 생성 (6자리 숫자)
//        String verificationCode = generateVerificationCode();
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10); // 인증 코드는 10분 동안 유효


        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(verificationCode);
        emailVerification.setCreateAt(createAt);
        emailVerification.setExpiresAt(expiresAt);
        emailVerification.setMember(null);
        emailVerificationRepository.save(emailVerification);

        // 2. 사용자 이메일로 인증 코드 전송
//        sendEmail(email, verificationCode);
    }

    public ResponseEntity<?> checkEmailToken(String code) {
        Optional<EmailVerification> optionalEmailVerification = emailVerificationRepository.findByVerificationCode(code);

        if(optionalEmailVerification.isPresent()) { // 토큰 존재
            EmailVerification emailVerification = optionalEmailVerification.get();

            // 유효한 토큰
            if(!emailVerification.getExpiresAt().isBefore(LocalDateTime.now())){
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("message", "Check email token successfully");

                return ResponseEntity.ok().body(responseBody);
            }
            else {  // 토큰 만료
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "Verification token has expired");
                return ResponseEntity.badRequest().body(responseBody);
            }
        }
        else {  // 토큰 존재하지 않음
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "server error");
            responseBody.put("message", "Invalid verification token");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
