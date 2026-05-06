package com.gooroomees.neulbomgil_backend.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Role role;

    private Status status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}