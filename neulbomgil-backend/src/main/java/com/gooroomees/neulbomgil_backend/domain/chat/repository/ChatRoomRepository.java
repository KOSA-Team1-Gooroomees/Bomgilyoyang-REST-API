package com.gooroomees.neulbomgil_backend.domain.chat.repository;


import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
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

    @Query("""
    select new com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto(
        cr.roomId,
        u.userId,
        u.name,
        c.message,
        cr.lastMessageAt
    )
    from ChatRoom cr
    join cr.user u
    left join Chat c
        on c.chatRoom = cr
       and c.createdAt = (
            select max(c2.createdAt)
            from Chat c2
            where c2.chatRoom = cr
       )
    order by cr.lastMessageAt desc
""")
    List<ChatRoomResponseDto> findAllChatRoomResponses();


}
