package com.project.todolist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/savelist")
public class SaveListController {
    @GetMapping("/show")
    public ResponseEntity<?> show() {
        List<Map<String, Object>> lists = new ArrayList<>();

        Map<String, Object> listItem1 = new HashMap<>();
        listItem1.put("id", 1);
        listItem1.put("title", "List Title 1");
        lists.add(listItem1);

        Map<String, Object> listItem2 = new HashMap<>();
        listItem2.put("id", 2);
        listItem2.put("title", "List Title 2");
        lists.add(listItem2);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("lists", lists);

        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List title updated successfully");

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "List deleted successfully");

        return ResponseEntity.ok().body(responseBody);
    }
}
