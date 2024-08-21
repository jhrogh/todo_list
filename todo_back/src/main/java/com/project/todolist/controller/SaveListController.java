package com.project.todolist.controller;

import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.service.SaveHomeList;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/savelist")
public class SaveListController {
    private final SaveHomeList saveHomeList;
    @GetMapping("/show")
    public ResponseEntity<?> show(HttpServletRequest request) {
        return saveHomeList.showSaveList(request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        return saveHomeList.updateSaveList(request, updateCheckListDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestParam("id") Long id) {
        return saveHomeList.deleteSaveList(request, id);
    }
}
