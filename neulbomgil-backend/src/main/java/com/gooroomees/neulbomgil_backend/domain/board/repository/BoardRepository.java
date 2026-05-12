package com.gooroomees.neulbomgil_backend.domain.board.repository;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainingOrContentContaining(
            String title, String content, Pageable pageable);
    Long countByUser(UserAuth user);
}
