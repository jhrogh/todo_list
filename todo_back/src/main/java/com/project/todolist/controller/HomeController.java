package com.project.todolist.controller;

import com.project.todolist.dto.IsCheckedHomeDto;
import com.project.todolist.service.HomeList;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody IsCheckedHomeDto isCheckedHomeDto) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/delete-list")
    public ResponseEntity<?> deleteList() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List deleted successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List saved successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAll() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "All lists deleted successfully");

        return ResponseEntity.ok().body(responseBody);
    }
}
