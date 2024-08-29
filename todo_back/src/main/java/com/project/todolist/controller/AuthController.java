package com.project.todolist.controller;

import com.project.todolist.dto.CheckMemberDto;
import com.project.todolist.dto.JoinDto;
import com.project.todolist.dto.LoginDto;
import com.project.todolist.dto.VerifyEmailDto;
import com.project.todolist.service.CheckLoginService;
import com.project.todolist.service.JoinMemberService;
import com.project.todolist.service.JwtTokenService;
import com.project.todolist.service.MypageService;
import com.project.todolist.service.VerifyEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 및 회원가입", description = "로그인 및 회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final JoinMemberService joinMemberService;
    private final VerifyEmailService verifyEmailService;
    private final CheckLoginService checkLoginService;
    private final JwtTokenService jwtTokenService;
    private final MypageService mypageService;

    @GetMapping("/check-auth")      // 로그인 인증 여부 (jwt 토큰)
    @Operation(summary = "jwt 토큰 유무", description = "jwt 토큰으로 로그인 인증 여부 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 유효하지 않은 토큰"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : 토큰 존재하지 않음"),
    })
    public ResponseEntity<?> checkAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtTokenService.findToken(request);

        if(token != null) {
            boolean isValidateToken = jwtTokenService.validateToken(token);        // false: 만료됨

            if(isValidateToken) {
                String memberId = jwtTokenService.findMemberId(token);
                jwtTokenService.saveToken(response, jwtTokenService.createToken(memberId));

                String name = mypageService.findUserName(request);
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("name", name);

                return ResponseEntity.ok().body(responseBody);
            }
            else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "failed");
                responseBody.put("message", "Token is not Validate");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
            }
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "server error");
            responseBody.put("message", "Authentication required");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
    })
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return checkLoginService.logoutUser(request, response);
    }

    @PostMapping("/login")      // 로그인하기
    @Operation(summary = "로그인", description = "jwt 토큰 생성 및 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        CheckMemberDto result = checkLoginService.checkMember(loginDto);

        if(result.isSuccess()) {
            String token = jwtTokenService.createToken(result.getMemberId());
            jwtTokenService.saveToken(response, token);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", result.getMessage());

            return ResponseEntity.ok().body(responseBody);
        } else{
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "failed");
            responseBody.put("message", result.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @GetMapping("/join/memberId/unique")
    @Operation(summary = "아이디 중복 체크")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "badRequest : 중복된 아이디"),
    })
    public ResponseEntity<?> uniqueMemberId(@RequestParam("memberId") String memberId) {
        return joinMemberService.checkMemberId(memberId);
    }

    @PostMapping("/join/verify-email")      // 회원가입 - 이메일 인증
    @Operation(summary = "이메일 인증 코드 전송", description = "전달받은 이메일로 인증코드 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailDto verifyEmailDto) {
        verifyEmailService.sendEmailCode(verifyEmailDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Email verified successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/join/verify-email/code")
    @Operation(summary = "이메일 인증 코드 일치 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "badRequest"),
    })
    public ResponseEntity<?> verifyEmailCode(@RequestParam("code") String code) {
        return verifyEmailService.checkEmailToken(code);
    }

    @PostMapping("/join")       // 회원가입 - 가입하기
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        return joinMemberService.saveMember(joinDto);
    }
}
