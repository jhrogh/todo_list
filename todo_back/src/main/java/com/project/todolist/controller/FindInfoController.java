package com.project.todolist.controller;

import com.project.todolist.dto.ChangePwDto;
import com.project.todolist.dto.FindIdDto;
import com.project.todolist.dto.FindPwDto;
import com.project.todolist.service.FindInfoService;
import com.project.todolist.service.VerifyEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "아이디, 비밀번호 찾기", description = "아이디, 비밀번호 찾기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/find")
public class FindInfoController {
    private final FindInfoService findInfoService;
    private final VerifyEmailService verifyEmailService;

    @PostMapping("/id")
    @Operation(summary = "아이디 찾기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "badRequest : 이메일 일치하지 않음"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : 가입된 아이디 없음"),
    })
    public ResponseEntity<?> findId(@RequestBody FindIdDto findIdDto) {
        // 이름, 이메일 전달 받음
        return findInfoService.searchId(findIdDto);
        // 이름으로 찾은 아이디 (이메일을 공통으로 사용중이라)
        // 이메일로 찾은 이름 = 전달받은 이름 => 아이디 반환
    }

    @PostMapping("/pw/verify-email")
    @Operation(summary = "비밀번호 찾기 - 이메일 인증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "badRequest"),
    })
    public ResponseEntity<?> pwVerifyEmail(@RequestBody FindPwDto findPwDto) {
        return findInfoService.searchPwEmailVerification(findPwDto);
    }

    @GetMapping("/pw/verify-email/code")
    @Operation(summary = "비밀번호 찾기 - 이메일 인증 코드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "badRequest : 인증코드 만료"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : 인증코드 없음"),
    })
    public ResponseEntity<?> pwEmailCode(@RequestParam("code") String code) {
        return verifyEmailService.checkEmailToken(code);
    }

    @PostMapping("/changepw")
    @Operation(summary = "비밀번호 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> changePw(@RequestBody ChangePwDto changePwDto) {
        return findInfoService.changePassword(changePwDto);
    }
}
