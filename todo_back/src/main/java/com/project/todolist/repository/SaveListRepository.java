package com.project.todolist.repository;

import com.project.todolist.entity.Member;
import com.project.todolist.entity.SaveList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveListRepository extends JpaRepository<SaveList, Long> {
    List<SaveList> findByMember(Member member);
    Optional<SaveList> findById(Long id);
}
