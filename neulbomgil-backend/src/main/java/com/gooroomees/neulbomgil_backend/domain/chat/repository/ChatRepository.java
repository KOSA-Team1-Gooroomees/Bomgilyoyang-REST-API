package com.gooroomees.neulbomgil_backend.domain.chat.repository;

import com.gooroomees.neulbomgil_backend.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
