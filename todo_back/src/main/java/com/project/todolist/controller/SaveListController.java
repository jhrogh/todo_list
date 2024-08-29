package com.project.todolist.controller;

import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.service.SaveHomeListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "저장 목록", description = "저장 목록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/savelist")
public class SaveListController {
    private final SaveHomeListService saveHomeListService;
    @GetMapping("/show")
    @Operation(summary = "저장목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> show(HttpServletRequest request) {
        return saveHomeListService.showSaveList(request);
    }

    @PostMapping("/update")
    @Operation(summary = "제목 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        return saveHomeListService.updateSaveList(request, updateCheckListDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "저장목록 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestParam("id") Long id) {
        return saveHomeListService.deleteSaveList(request, id);
    }
}
