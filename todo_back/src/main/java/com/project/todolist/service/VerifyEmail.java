package com.project.todolist.service;

import com.project.todolist.dto.VerifyEmailDto;
import com.project.todolist.entity.EmailVerification;
import com.project.todolist.repository.EmailVerificationRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmail {
    private final EmailVerificationRepository emailVerificationRepository;
    private final CreateEmailCode createEmailCode;

    public void sendEmailCode(VerifyEmailDto verifyEmailDto) {
        // 1. 이메일 저장
        String email = verifyEmailDto.getEmail();
        String verificationCode = createEmailCode.emailCode(email);
        // 인증 코드 생성 (6자리 숫자)
//        String verificationCode = generateVerificationCode();
        Timestamp createAt = Timestamp.from(Instant.now());
        Timestamp expiresAt = Timestamp.from(Instant.now().plus(10, ChronoUnit.MINUTES)); // 인증 코드는 10분 동안 유효


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

        if(optionalEmailVerification.isPresent()) {
            EmailVerification emailVerification = optionalEmailVerification.get();
            LocalDateTime nowLocalDateTime = LocalDateTime.now();
            Timestamp nowTimestamp = Timestamp.from(nowLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
            // 인증코드 존재
            if(!emailVerification.getExpiresAt().before(nowTimestamp)){
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("message", "Check email token successfully");

                return ResponseEntity.ok().body(responseBody);
            }
            else {  // 인증코드 만료
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "Verification token has expired");
                return ResponseEntity.badRequest().body(responseBody);
            }
        }
        else {  // 인증코드 존재하지 않음
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "server error");
            responseBody.put("message", "Token Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }
}
