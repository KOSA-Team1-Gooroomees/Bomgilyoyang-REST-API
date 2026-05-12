package com.gooroomees.neulbomgil_backend.domain.admin.controller;

import com.gooroomees.neulbomgil_backend.domain.admin.dto.AdminUserResponseDto;
import com.gooroomees.neulbomgil_backend.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
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


    @PatchMapping("/users/{userId}/status")
    public void updateUserStatus(@PathVariable Long userId) {
        adminService.updateUserStatus(userId);
    }

}
