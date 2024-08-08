package com.project.todolist.service;

import com.project.todolist.dto.CheckMemberDto;
import com.project.todolist.dto.LoginDto;
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

@Service
@RequiredArgsConstructor
public class CheckLogin {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;
    private final JwtToken jwtToken;

    public CheckMemberDto checkMember(LoginDto loginDto) {
        // 아이디로 디비 조회
        String memberId = loginDto.getMemberId();
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        CheckMemberDto result = new CheckMemberDto();

        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String password;
            try {
                password = hashPassword.hashPassword(loginDto.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Password hashing failed", e);
            }
            if(password.equals(member.getPassword())) { // id, pw 일치
                result.setSuccess(true);
                result.setMemberId(memberId);
                result.setMessage("Login successful");
            }
            else {      // pw 불일치
                result.setSuccess(false);
                result.setMessage("Failed Password");
            }
        }
        else {      // Id 미존재
            result.setSuccess(false);
            result.setMessage("Not Found Id");
        }
        return result;
    }

    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        if(!jwtToken.findToken(request).isEmpty()) {
            jwtToken.deleteToken(response);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Logout successfully");

            return ResponseEntity.ok().body(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Logout failed");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }
}
