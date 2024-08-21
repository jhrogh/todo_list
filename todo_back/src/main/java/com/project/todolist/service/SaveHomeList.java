package com.project.todolist.service;

import com.project.todolist.entity.Member;
import com.project.todolist.entity.SaveList;
import com.project.todolist.repository.CheckListRepository;
import com.project.todolist.repository.MemberRepository;
import com.project.todolist.repository.SaveListRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveHomeList {
    private final JwtToken jwtToken;
    private final MemberRepository memberRepository;
    private final CheckListRepository checkListRepository;
    private final SaveListRepository saveListRepository;

    public ResponseEntity<?> showSaveList(HttpServletRequest request) {
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();
        List<SaveList> saveLists = saveListRepository.findByMember(member);

        if (saveLists.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Not Found Lists");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("lists", saveLists);

            return ResponseEntity.ok().body(responseBody);
        }
    }


}
