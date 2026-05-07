package com.gooroomees.neulbomgil_backend.domain.chat.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.Role;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserChatRepository;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.entity.Chat;
import com.gooroomees.neulbomgil_backend.domain.chat.repository.ChatRoomRepository;
import com.gooroomees.neulbomgil_backend.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.gooroomees.neulbomgil_backend.domain.chat.entity.ChatRoom;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserChatRepository userAuthRepository;

    public ChatRoomResponseDto startChatRoom(Integer userId) {


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

    public List<ChatResponseDto> getMessageByRoomId(Integer roomId) {
        List<Chat> chats = chatRepository.FindMessagesByRoomId(roomId);
        List<ChatResponseDto> chatResponseDtoList = new ArrayList<>();
        ;
        for (Chat chat : chats) {

            chatResponseDtoList.add(
                    new ChatResponseDto(
                            chat.getChatId(),
                            chat.getChatRoom().getRoomId(),
                            chat.getSenderId(),
                            chat.getMessage(),
                            chat.getCreatedAt(),
                            chat.getReadAt()
                    )
            );
        }

        return chatResponseDtoList;
    }
}
