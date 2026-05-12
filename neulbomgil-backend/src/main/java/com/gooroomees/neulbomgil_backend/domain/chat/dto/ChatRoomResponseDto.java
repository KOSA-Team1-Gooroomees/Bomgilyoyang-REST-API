package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public record ChatRoomResponseDto(
        Long roomId,
        Long userId,
        String name,
        String lastMessage,
        LocalDateTime lastMessageAt
) {
}