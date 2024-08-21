package com.project.todolist.controller;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.SelectListAddDto;
import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.service.SelectList;
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
@RequestMapping("/api/savelist/selectlist")
public class SelectListController {
    private final SelectList selectList;
    @GetMapping("/id")
    public ResponseEntity<?> showList(@RequestParam("id") Long saveListId) {
        return selectList.showSelectList(saveListId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addList(HttpServletRequest request, @RequestBody SelectListAddDto selectListAddDto) {
        return selectList.addSelectList(request, selectListAddDto);
    }

    @PostMapping("/checkbox")
    public ResponseEntity<?> checkboxList(HttpServletRequest request, @RequestBody IsCheckedHomeDto isCheckedHomeDto) {
        return selectList.updateCheckbox(request, isCheckedHomeDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateList(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        return selectList.updateSelectList(request, updateCheckListDto);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteList(HttpServletRequest request, @RequestParam("id") Long id) {
        return selectList.deleteSelectList(request, id);
    }
}
