package com.gooroomees.neulbomgil_backend.domain.auth.repository;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAuth, Integer> {
    Optional<UserAuth> findByEmail(String email);
}