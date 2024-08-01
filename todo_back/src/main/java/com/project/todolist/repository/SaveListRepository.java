package com.project.todolist.repository;

import com.project.todolist.entity.SaveList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveListRepository extends JpaRepository<SaveList, Long> {
}
