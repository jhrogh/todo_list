package com.project.todolist.service;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.entity.CheckList;
import com.project.todolist.entity.Member;
import com.project.todolist.repository.CheckListRepository;
import com.project.todolist.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
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
        checkList.setUpdateAt(Timestamp.from(Instant.now()));
        checkList.setCreateAt(Timestamp.from(Instant.now()));
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

        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        CheckList checkList = optionalCheckList.get();

        if(checkList.getMember().equals(member) && member.getId().equals(checkList.getMember().getId())) {
            boolean isChecked = checkList.isChecked();
            checkList.setChecked(!isChecked);
            checkList.setUpdateAt(Timestamp.from(Instant.now()));

            checkListRepository.save(checkList);



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

    public ResponseEntity<?> updateList(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        Long id = updateCheckListDto.getId();

        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        CheckList checkList = optionalCheckList.get();

        if(checkList.getMember().equals(member) && member.getId().equals(checkList.getMember().getId())) {
            String content = updateCheckListDto.getContent();
            checkList.setContent(content);
            checkList.setUpdateAt(Timestamp.from(Instant.now()));

            checkListRepository.save(checkList);


            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Content updated successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Failed Content updated");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    public ResponseEntity<?> deleteList(HttpServletRequest request, Long id) {
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        CheckList checkList = optionalCheckList.get();

        if(checkList.getMember().equals(member) && member.getId().equals(checkList.getMember().getId())) {
            checkListRepository.delete(checkList);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Delete list successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Failed Delete list");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    public void saveAllList() {

    }

    public void deleteAllList() {

    }
}
