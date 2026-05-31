package com.gooroomees.neulbomgil_backend.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원 탈퇴 요청 정보")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {

    @Schema(description = "비밀번호 확인", example = "pass1234!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
