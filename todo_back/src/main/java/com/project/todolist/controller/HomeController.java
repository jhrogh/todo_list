package com.project.todolist.controller;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.dto.UpdateCheckListDto;
import com.project.todolist.service.HomeList;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final HomeList homeList;

    @GetMapping("/show")
    public ResponseEntity<?> show(HttpServletRequest request) {
        return homeList.showList(request);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(HttpServletRequest request, @RequestBody String content) {
        return homeList.addList(request, content);
    }

    @PostMapping("/checkbox")
    public ResponseEntity<?> checkbox(HttpServletRequest request, @RequestBody IsCheckedHomeDto isCheckedHomeDto) {
        return homeList.updateCheckbox(request, isCheckedHomeDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UpdateCheckListDto updateCheckListDto) {
        return homeList.updateList(request, updateCheckListDto);
    }

    @DeleteMapping("/delete-list")
    public ResponseEntity<?> deleteList(HttpServletRequest request, @RequestParam("id") Long id) {
        return homeList.deleteList(request, id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody Map<String, List<Long>> requestBody) {
        return homeList.saveAllList(request, requestBody);
    }

    @PostMapping("/delete-all")
    public ResponseEntity<?> deleteAll(HttpServletRequest request, @RequestBody Map<String, List<Long>> requestBody) {
        return homeList.deleteAllList(request, requestBody);
    }
}
