package com.project.todolist.controller;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.service.HomeListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "체크리스트", description = "체크리스트(HOME) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final HomeListService homeListService;

    @GetMapping("/show")
    @Operation(summary = "작성 중인 체크리스트 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success : isSaved = false"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : 작성한 체크리스트 없음"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : 체크리스트 존재 && 모든 isSaved = true"),
    })
    public ResponseEntity<?> show(HttpServletRequest request) {
        return homeListService.showList(request);
    }

    @PostMapping("/add")
    @Operation(summary = "체크리스트 추가 (isSaved=false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> add(HttpServletRequest request, @RequestBody String content) {
        return homeListService.addList(request, content);
    }

    @PostMapping("/checkbox")
    @Operation(summary = "체크박스 변경 (isSaved=false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> checkbox(HttpServletRequest request, @RequestBody IsCheckedHomeDto isCheckedHomeDto) {
        return homeListService.updateCheckbox(request, isCheckedHomeDto);
    }

    @PostMapping("/update")
    @Operation(summary = "체크리스트 내용 수정 (isSaved=false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        return homeListService.updateList(request, updateCheckListDto);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "체크리스트 내용 삭제 (isSaved=false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> deleteList(HttpServletRequest request, @RequestParam("id") Long id) {
        return homeListService.deleteList(request, id);
    }

    @PostMapping("/save")
    @Operation(summary = "전체 저장 (isSaved=false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody Map<String, List<Long>> requestBody) {
        return homeListService.saveAllList(request, requestBody);
    }

    @PostMapping("/delete-all")
    @Operation(summary = "전체 삭제 (isSaved=false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> deleteAll(HttpServletRequest request, @RequestBody Map<String, List<Long>> requestBody) {
        return homeListService.deleteAllList(request, requestBody);
    }
}
