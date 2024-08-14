package com.project.todolist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty("emailVerified")
    private boolean emailVerified = false;

    @Column(nullable = false)
    private String password;

    private Timestamp createAt;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private EmailVerification emailVerification;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<CheckList> checkList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<SaveList> saveList;
}
