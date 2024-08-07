package com.project.todolist.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // HS256 알고리즘에 적합한 키 생성
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // 키를 Base64로 인코딩하여 출력
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Key (Base64): " + base64Key);
    }
}
