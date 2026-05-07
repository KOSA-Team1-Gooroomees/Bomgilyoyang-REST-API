package com.gooroomees.neulbomgil_backend.domain.chat.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.Role;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserChatRepository;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.gooroomees.neulbomgil_backend.domain.chat.entity.ChatRoom;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRepository userAuthRepository;

    public ChatRoomResponseDto startChatRoom(Integer userId) {

        UserAuth admin = userAuthRepository.findFirstByRole(Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("관리자 없음"));

        UserAuth user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));


        ChatRoom chatRoom = chatRoomRepository
                .findChatRoom(user)
                .orElseGet(() -> chatRoomRepository.save(
                        ChatRoom.builder()
                                .user(user)
                                .build()
                ));

        return ChatRoomResponseDto.builder()
                .roomId(chatRoom.getRoomId())
                .userId(chatRoom.getUser().getUserId())
                .lastMessageAt(chatRoom.getLastMessageAt())
                .build();
    }
}
