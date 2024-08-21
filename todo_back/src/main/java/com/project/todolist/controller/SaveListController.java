package com.project.todolist.controller;

import com.project.todolist.service.SaveHomeList;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> update() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List title updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List deleted successfully");

        return ResponseEntity.ok().body(responseBody);
    }
}
