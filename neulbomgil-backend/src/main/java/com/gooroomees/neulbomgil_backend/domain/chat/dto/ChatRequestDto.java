package com.gooroomees.neulbomgil_backend.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public record ChatRequestDto(
        String message
) {
}
