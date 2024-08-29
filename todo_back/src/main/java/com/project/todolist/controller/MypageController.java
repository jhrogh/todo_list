package com.project.todolist.controller;

import com.project.todolist.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "마이페이지", description = "마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypageService")
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/show")
    @Operation(summary = "사용자 정보 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> show(HttpServletRequest request) {
        return mypageService.showMyInfo(request);
    }

    @DeleteMapping("/delete-account")
    @Operation(summary = "회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "badRequest : 비밀번호 일치하지 않음"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
    })
    public ResponseEntity<?> deleteAccount(HttpServletRequest request, @RequestBody Map<String, String> password, HttpServletResponse response) {
        return mypageService.deleteAccount(request, password, response);
    }
}
