package com.gooroomees.neulbomgil_backend.domain.admin.controller;

import com.gooroomees.neulbomgil_backend.domain.admin.dto.AdminUserResponseDto;
import com.gooroomees.neulbomgil_backend.domain.admin.service.AdminService;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 전체 사용자 조회
     */
    @GetMapping("/users")
    public List<AdminUserResponseDto> getUsers() {
        return adminService.getUsers();
    }

    /**
     * 탈퇴 사용자 조회
     */
    @GetMapping("/users/deleted")
    public List<AdminUserResponseDto> getDeletedUsers() {
        return adminService.getDeletedUsers();
    }


    @PatchMapping("/users/status")
    public void updateUserStatus(@AuthenticationPrincipal UserAuth userAuth)
    {
        adminService.updateUserStatus(userAuth.getUserId());
    }

}
