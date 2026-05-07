package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRequestDto {

    private Long roomId;

    private Long senderId;

    private String message;
}
