package com.project.todolist.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    @PostMapping("/add")
    public ResponseEntity<?> add() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List added successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/checkbox")
    public ResponseEntity<?> checkbox() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "Checkbox status updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update() {
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
