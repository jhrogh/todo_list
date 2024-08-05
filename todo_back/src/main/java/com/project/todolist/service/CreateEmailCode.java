package com.project.todolist.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEmailCode {
    private final JavaMailSender mailSender;

    public String emailCode(String email) {
        String verificationCode = generateVerificationCode();
        sendEmail(email, verificationCode);
        return verificationCode;
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 100000부터 999999까지의 숫자
        return String.valueOf(code);
    }
    private void sendEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("ToDo List 이메일 인증");
        message.setText("인증코드 6자리를 입력해주세요. 인증코드: " + verificationCode);

        mailSender.send(message);
    }
}
