package com.project.todolist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CHECK_LIST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @JsonProperty("isChecked")
    private boolean isChecked;

    @Column(nullable = false)
    @JsonProperty("isSaved")
    private boolean isSaved;

    @Column(nullable = false)
    private Timestamp createAt;

    @Column(nullable = false)
    private Timestamp updateAt;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "save_list_id", referencedColumnName = "id")
    @JsonBackReference
    private SaveList saveList;
}
