package com.project.todolist.service;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.entity.CheckList;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.CheckListRepository;
import com.project.todolist.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class HomeList {
    private final JwtToken jwtToken;
    private final CheckListRepository checkListRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<?> showList(HttpServletRequest request) {
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();
        List<CheckList> checkLists = checkListRepository.findByMember(member);

        if (checkLists.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Not Found Lists");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("lists", checkLists);

            return ResponseEntity.ok().body(responseBody);
        }
    }

    public ResponseEntity<?> addList(HttpServletRequest request, String content) {
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        CheckList checkList = new CheckList();
        checkList.setContent(content);
        checkList.setChecked(false);
        checkList.setSaved(false);
        checkList.setUpdateAt(LocalDateTime.now());
        checkList.setCreateAt(LocalDateTime.now());
        checkList.setMember(member);
        checkList.setSaveList(null);

        checkListRepository.save(checkList);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("content", checkList.getContent());
        responseBody.put("isChecked", checkList.isChecked());

        return ResponseEntity.ok().body(responseBody);
    }

    public ResponseEntity<?> updateCheckbox(HttpServletRequest request, @RequestBody IsCheckedHomeDto isCheckedHomeDto) {
        Long id = isCheckedHomeDto.getId();
//        boolean isChecked = isCheckedHomeDto.isChecked();
//        System.out.println("전달받은: " + isChecked);

        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        CheckList checkList = optionalCheckList.get();

        if(checkList.getMember().equals(member) && member.getId().equals(checkList.getMember().getId())) {
            boolean isChecked = checkList.isChecked();
            checkList.setChecked(!isChecked);
            checkList.setUpdateAt(LocalDateTime.now());
            System.out.println("Before save: " + isChecked);
            checkListRepository.save(checkList);
            System.out.println("After save: " + !isChecked);


            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Checkbox status updated successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Failed Checkbox status updated");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    public void updateList() {

    }

    public void deleteList() {

    }

    public void saveAllList() {

    }

    public void deleteAllList() {

    }
}
