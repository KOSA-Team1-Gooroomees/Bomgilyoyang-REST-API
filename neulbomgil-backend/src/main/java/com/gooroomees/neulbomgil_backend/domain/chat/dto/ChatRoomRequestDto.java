package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {

    private Long adminId;

    private Long userId;
}