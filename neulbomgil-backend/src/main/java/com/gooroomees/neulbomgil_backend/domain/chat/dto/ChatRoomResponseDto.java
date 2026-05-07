package com.gooroomees.neulbomgil_backend.domain.chat.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private Integer roomId;

    private Integer userId;

    private LocalDateTime lastMessageAt;
}
