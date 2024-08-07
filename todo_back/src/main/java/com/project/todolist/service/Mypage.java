package com.project.todolist.service;

import com.project.todolist.entity.Member;
import com.project.todolist.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Mypage {
    private final JwtToken jwtToken;
    private final MemberRepository memberRepository;
    public ResponseEntity<?> showMyInfo(HttpServletRequest request) {
        String token = jwtToken.findToken(request);
        String memberId = jwtToken.findMemberId(token);

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Map<String, Object> user = new HashMap<>();
        user.put("memberId", member.getMemberId());
        user.put("name", member.getName());
        user.put("email", member.getEmail());

        // 응답 본문 생성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("user", user);

        return ResponseEntity.ok().body(responseBody);
    }

    public String findUserName(HttpServletRequest request) {
        String token = jwtToken.findToken(request);
        String memberId = jwtToken.findMemberId(token);

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        return member.getName();
    }
}
