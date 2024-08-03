package com.project.todolist.service;

import com.project.todolist.dto.JoinDto;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.MemberRepository;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinMember {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;

    public void saveMember(JoinDto joinDto) {
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
    }
}
