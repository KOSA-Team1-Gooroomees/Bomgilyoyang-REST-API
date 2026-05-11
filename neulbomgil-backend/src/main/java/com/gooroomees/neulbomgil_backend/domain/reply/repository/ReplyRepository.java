package com.gooroomees.neulbomgil_backend.domain.reply.repository;

import com.gooroomees.neulbomgil_backend.domain.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByBoard_Boardid(Long boardId, Pageable pageable);
}
