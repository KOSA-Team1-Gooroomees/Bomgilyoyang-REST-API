package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRequestDto {

    private Integer roomId;

    private Integer senderId;

    private String message;
}
