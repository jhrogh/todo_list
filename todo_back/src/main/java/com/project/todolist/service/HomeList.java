package com.project.todolist.service;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.entity.CheckList;
import com.project.todolist.entity.Member;
import com.project.todolist.entity.SaveList;
import com.project.todolist.repository.CheckListRepository;
import com.project.todolist.repository.MemberRepository;
import com.project.todolist.repository.SaveListRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private final SaveListRepository saveListRepository;

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
            List<CheckList> filteredCheckLists = checkLists.stream()
                    .filter(checkList -> checkList.isSaved() == false)
                    .collect(Collectors.toList());

            if (filteredCheckLists.isEmpty()) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "No Lists with isSaved = null");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
            } else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("lists", filteredCheckLists);

                return ResponseEntity.ok().body(responseBody);
            }
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
        responseBody.put("id", checkList.getId());
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

    public ResponseEntity<?> saveAllList(HttpServletRequest request, @RequestBody Map<String, List<Long>> requestBody) {
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        List<Long> ids = requestBody.get("ids");
        List<CheckList> checkLists = checkListRepository.findAllByIdInAndIsSavedFalse(ids);

        if(checkLists.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Failed Save Lists All");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else {
            SaveList saveList = new SaveList();
            saveList.setMember(member);
            saveList.setCreateAt(Timestamp.from(Instant.now()));
            saveList.setUpdateAt(Timestamp.from(Instant.now()));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            saveList.setTitle(formattedNow);

            saveListRepository.save(saveList);
            for (CheckList cl : checkLists) {
                cl.setSaveList(saveList);
                cl.setSaved(true); // 저장되었음을 표시
                checkListRepository.save(cl);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Save Lists All successfully");

            return ResponseEntity.ok().body(responseBody);
        }
    }

    public ResponseEntity<?> deleteAllList(HttpServletRequest request, @RequestBody Map<String, List<Long>> requestBody) {
        String memberId = jwtToken.findMemberId(jwtToken.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        List<Long> ids = requestBody.get("ids");
        List<CheckList> checkLists = checkListRepository.findAllByIdInAndIsSavedFalse(ids);

        if(checkLists.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Failed Delete Lists All");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else {
            checkListRepository.deleteAll(checkLists);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Delete Lists All successfully");

            return ResponseEntity.ok().body(responseBody);
        }
    }
}
