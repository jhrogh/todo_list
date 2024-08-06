package com.project.todolist.service;

import com.project.todolist.dto.CheckMemberDto;
import com.project.todolist.dto.LoginDto;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.MemberRepository;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckLogin {
    private final MemberRepository memberRepository;
    private final HashPassword hashPassword;

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
}
