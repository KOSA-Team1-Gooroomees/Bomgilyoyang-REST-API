package com.gooroomees.neulbomgil_backend.domain.chat.repository;


import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("""
select cr from ChatRoom cr  where cr.user = :user 

""")
    Optional<ChatRoom> findChatRoom( @Param("user") UserAuth user);


}
