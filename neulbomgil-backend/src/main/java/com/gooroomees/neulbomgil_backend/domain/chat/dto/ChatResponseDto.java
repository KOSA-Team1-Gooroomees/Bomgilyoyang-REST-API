package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ChatResponseDto {

    private Integer chatId;

    private Integer roomId;

    private Integer senderId;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

}