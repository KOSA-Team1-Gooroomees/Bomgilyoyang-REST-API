package com.gooroomees.neulbomgil_backend.domain.chat.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private Long roomId;

    private Long adminId;

    private Long userId;

    private LocalDateTime lastMessageAt;
}
