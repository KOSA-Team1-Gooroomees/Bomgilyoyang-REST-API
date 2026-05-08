package com.gooroomees.neulbomgil_backend.domain.chat.repository;


import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
select cr from ChatRoom cr  where cr.user = :user 

""")
    Optional<ChatRoom> findChatRoom( @Param("user") UserAuth user);

    List<ChatRoom> findAllByOrderByLastMessageAtDesc();

    @Query(
            """
select cr from ChatRoom cr where cr.roomId = :roomId
"""

    )
    Optional<ChatRoom> findById(Long roomId);

}
