package com.project.todolist.service;

import com.project.todolist.dto.UpdateCheckListDto;
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
public class SaveHomeListService {
    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;
    private final CheckListRepository checkListRepository;
    private final SaveListRepository saveListRepository;

    public ResponseEntity<?> showSaveList(HttpServletRequest request) {
        String memberId = jwtTokenService.findMemberId(jwtTokenService.findToken(request));
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

    public ResponseEntity<?> updateSaveList(HttpServletRequest request,
                                            @RequestBody UpdateCheckListDto updateCheckListDto) {
        Long id = updateCheckListDto.getId();
        Optional<SaveList> optionalSaveList = saveListRepository.findById(id);
        SaveList saveList = optionalSaveList.get();

        String title = updateCheckListDto.getContent();
        saveList.setTitle(title);
        saveList.setUpdateAt(Timestamp.from(Instant.now()));

        saveListRepository.save(saveList);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Title updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    public ResponseEntity<?> deleteSaveList(HttpServletRequest request, @RequestParam("id") Long id) {
        String memberId = jwtTokenService.findMemberId(jwtTokenService.findToken(request));
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.get();

        Optional<SaveList> optionalSaveList = saveListRepository.findById(id);
        SaveList saveList = optionalSaveList.get();

        if (saveList.getMember().equals(member)) {
            saveListRepository.delete(saveList);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Delete savelist successfully");

            return ResponseEntity.ok().body(responseBody);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", "Failed Delete savelist");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }
}
