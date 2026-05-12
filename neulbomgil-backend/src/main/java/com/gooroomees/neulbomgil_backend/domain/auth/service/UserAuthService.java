package com.gooroomees.neulbomgil_backend.domain.auth.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;

    public UserAuth findById(Long userId) {
        return userAuthRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    // 사용자 수정
    public boolean changeUser(UserAuth newUser) {
        userAuthRepository.findById(newUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        try {
            userAuthRepository.save(newUser);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    // 사용자 삭제
    public boolean deleteUser(Long userId) {
        try {
            userAuthRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }
}