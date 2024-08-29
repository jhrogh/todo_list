package com.project.todolist.service;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.SelectListAddDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class SelectListService {
    private final JwtTokenService jwtTokenService;
    private final CheckListRepository checkListRepository;
    private final MemberRepository memberRepository;
    private final SaveListRepository saveListRepository;

    public ResponseEntity<?> showSelectList(@RequestParam("id") Long saveListId) {
        List<CheckList> checkLists = checkListRepository.findBySaveListId(saveListId);
        if (!checkLists.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("lists", checkLists);

            return ResponseEntity.ok().body(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Not Found Lists");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    public ResponseEntity<?> addSelectList(HttpServletRequest request, @RequestBody SelectListAddDto selectListAddDto) {
        Long saveListId = selectListAddDto.getId();
        String content = selectListAddDto.getContent();
        String memberId = jwtTokenService.findMemberId(jwtTokenService.findToken(request));

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Optional<SaveList> optionalSaveLists = saveListRepository.findById(saveListId);
        SaveList saveList = optionalSaveLists.get();

        CheckList checkList = new CheckList();
        checkList.setContent(content);
        checkList.setChecked(false);
        checkList.setSaved(true);
        checkList.setUpdateAt(Timestamp.from(Instant.now()));
        checkList.setCreateAt(Timestamp.from(Instant.now()));
        checkList.setMember(member);
        checkList.setSaveList(saveList);

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
        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        CheckList checkList = optionalCheckList.get();
        boolean isChecked = checkList.isChecked();

        checkList.setChecked(!isChecked);
        checkList.setUpdateAt(Timestamp.from(Instant.now()));

        checkListRepository.save(checkList);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Checkbox status updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    public ResponseEntity<?> updateSelectList(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        Long id = updateCheckListDto.getId();
        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        CheckList checkList = optionalCheckList.get();

        String content = updateCheckListDto.getContent();
        checkList.setContent(content);
        checkList.setUpdateAt(Timestamp.from(Instant.now()));

        checkListRepository.save(checkList);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Content updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    public ResponseEntity<?> deleteSelectList(HttpServletRequest request, Long id) {
        String memberId = jwtTokenService.findMemberId(jwtTokenService.findToken(request));
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
}
