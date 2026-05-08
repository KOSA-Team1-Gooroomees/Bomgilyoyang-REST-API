package com.gooroomees.neulbomgil_backend.domain.auth.repository;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserMypage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMypageRepository extends JpaRepository<UserMypage, Integer> {
}