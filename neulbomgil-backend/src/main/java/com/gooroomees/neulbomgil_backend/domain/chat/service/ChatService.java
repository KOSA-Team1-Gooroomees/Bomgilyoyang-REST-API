package com.gooroomees.neulbomgil_backend.domain.chat.service;


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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserChatRepository userAuthRepository;

    public ChatRoomResponseDto startChatRoom(Long userId) {


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
                .userId(userId)
                .lastMessageAt(chatRoom.getLastMessageAt())
                .build();
    }

    public List<ChatResponseDto> getMessageByRoomId(Long roomId) {
        List<Chat> chats = chatRepository.FindMessagesByRoomId(roomId);
        List<ChatResponseDto> chatResponseDtoList = new ArrayList<>();

        for (Chat chat : chats) {

            chatResponseDtoList.add(
                    ChatResponseDto.builder()
                            .chatId(chat.getChatId())
                            .roomId(chat.getChatRoom().getRoomId())
                            .senderId(chat.getSender().getUserId())
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

    public ChatResponseDto saveMessage(Long roomId, ChatRequestDto requestDto) {

        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방이 없습니다."));;

        UserAuth sender = userAuthRepository.findById(requestDto.getSenderId())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Chat chat = Chat.create(
                room,
                sender,
                requestDto.getMessage()
        );

        Chat savedChat = chatRepository.save(chat);


        return ChatResponseDto.builder()
                .chatId(savedChat.getChatId())
                .roomId(roomId)
                .senderId(sender.getUserId())
                .message(savedChat.getMessage())
                .createdAt(savedChat.getCreatedAt())
                .readAt(savedChat.getReadAt())
                .build();
    }
    @Transactional
    public void readMessages(Long roomId,Long senderId) {
        chatRepository.updateReadAt(roomId,senderId);
    }

    public boolean hasUnreadChats(Long userId) {
       return chatRepository.existsUnreadChatByUserId(userId);
    }
}
