package com.project.todolist.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class HashPasswordService {
    // 비밀번호를 SHA-256 해시로 변환합니다.
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    // 비밀번호를 검증하는 메서드
    public boolean validatePassword(String enteredPassword, String storedHash) throws NoSuchAlgorithmException {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return storedHash.equals(hashedEnteredPassword);
    }
}
