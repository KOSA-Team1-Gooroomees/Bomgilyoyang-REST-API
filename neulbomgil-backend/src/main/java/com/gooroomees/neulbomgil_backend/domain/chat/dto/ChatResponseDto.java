package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public record ChatResponseDto(
        Long chatId,
        Long roomId,
        Long senderId,
        String message,
        LocalDateTime createdAt,
        LocalDateTime readAt
) {
}