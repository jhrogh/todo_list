package com.project.todolist.controller;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.SelectListAddDto;
import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.service.SelectListService;
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

@Tag(name = "저장된 목록 상세보기", description = "저장된 목록 상세보기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/savelist/selectlist")
public class SelectListController {
    private final SelectListService selectListService;
    @GetMapping("/id")
    @Operation(summary = "선택된 저장목록의 체크리스트 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> showList(@RequestParam("id") Long saveListId) {
        return selectListService.showSelectList(saveListId);
    }

    @PostMapping("/add")
    @Operation(summary = "체크리스트 추가 (isSaved=true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> addList(HttpServletRequest request, @RequestBody SelectListAddDto selectListAddDto) {
        return selectListService.addSelectList(request, selectListAddDto);
    }

    @PostMapping("/checkbox")
    @Operation(summary = "체크박스 변경 (isSaved=true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> checkboxList(HttpServletRequest request, @RequestBody IsCheckedHomeDto isCheckedHomeDto) {
        return selectListService.updateCheckbox(request, isCheckedHomeDto);
    }

    @PostMapping("/update")
    @Operation(summary = "체크리스트 내용 수정 (isSaved=true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
    })
    public ResponseEntity<?> updateList(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        return selectListService.updateSelectList(request, updateCheckListDto);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "체크리스트 내용 삭제 (isSaved=true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<?> deleteList(HttpServletRequest request, @RequestParam("id") Long id) {
        return selectListService.deleteSelectList(request, id);
    }
}
