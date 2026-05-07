package com.gooroomees.neulbomgil_backend.domain.chat.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.Role;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserChatRepository;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRequestDto;
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

        for (Chat chat : chats) {

            chatResponseDtoList.add(
                    ChatResponseDto.builder()
                            .chatId(chat.getChatId())
                            .roomId(chat.getChatRoom().getRoomId())
                            .senderId(chat.getSenderId())
                            .message(chat.getMessage())
                            .createdAt(chat.getCreatedAt())
                            .readAt(chat.getReadAt())
                            .build()
            );
        }

        return chatResponseDtoList;
    }

    public List<ChatRoomResponseDto> getAllChatRooms() {
        List<ChatRoom> rooms =
                chatRoomRepository.findAllByOrderByLastMessageAtDesc();

        List<ChatRoomResponseDto> chatRoomResponseDto = new ArrayList<>();

        for (ChatRoom room : rooms) {
            chatRoomResponseDto.add(
            ChatRoomResponseDto.builder()
                    .roomId(room.getRoomId())
                    .userId(room.getUser().getUserId())
                    .lastMessageAt(room.getLastMessageAt())
                    .build()
                  );
        }

        return chatRoomResponseDto;
    }

    public ChatResponseDto saveMessage(Integer roomId, ChatRequestDto requestDto) {

        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방이 없습니다."));;

        Chat chat = Chat.create(
                room,
                requestDto.getSenderId(),
                requestDto.getMessage()
        );

        Chat savedChat = chatRepository.save(chat);


        return ChatResponseDto.builder()
                .chatId(savedChat.getChatId())
                .roomId(roomId)
                .senderId(savedChat.getSenderId())
                .message(savedChat.getMessage())
                .createdAt(savedChat.getCreatedAt())
                .readAt(savedChat.getReadAt())
                .build();
    }
}
