package com.gooroomees.neulbomgil_backend.domain.admin.dto;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminUserResponseDto {


    private Long userId;


    private String name;


    private String email;


    private Long boardCount;


    private Long replyCount;

    private Status status;

    private String  createdAt;
}
