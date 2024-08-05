package com.project.todolist.controller;

import com.project.todolist.dto.ChangePwDto;
import com.project.todolist.dto.FindIdDto;
import com.project.todolist.dto.FindPwDto;
import com.project.todolist.service.FindInfo;
import com.project.todolist.service.VerifyEmail;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/find")
public class FindInfoController {
    private final FindInfo findInfo;
    private final VerifyEmail verifyEmail;

    @PostMapping("/id")
    public ResponseEntity<?> findId(@RequestBody FindIdDto findIdDto) {
        // 이름, 이메일 전달 받음
        return findInfo.searchId(findIdDto);
        // 이름으로 찾은 아이디 (이메일을 공통으로 사용중이라)
        // 이메일로 찾은 이름 = 전달받은 이름 => 아이디 반환
    }

    @PostMapping("/pw/verify-email")
    public ResponseEntity<?> pwVerifyEmail(@RequestBody FindPwDto findPwDto) {
        return findInfo.searchPwEmailVerification(findPwDto);
    }

    @GetMapping("/pw/verify-email/code")
    public ResponseEntity<?> pwEmailCode(@RequestParam("code") String code) {
        return verifyEmail.checkEmailToken(code);
    }

    @PostMapping("/changepw")
    public ResponseEntity<?> changePw(@RequestBody ChangePwDto changePwDto) {
        findInfo.changePassword(changePwDto);


        Boolean isChangePw = null;

        if(isChangePw) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Password successfully change");

            return ResponseEntity.ok().body(responseBody);
        }
        else {  // 400 error
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Password reset token is invalid or expired");

            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
