package com.project.todolist.repository;

import com.project.todolist.entity.CheckList;
import com.project.todolist.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    List<CheckList> findByMember(Member member);

    Optional<CheckList> findById(Long id);
}
