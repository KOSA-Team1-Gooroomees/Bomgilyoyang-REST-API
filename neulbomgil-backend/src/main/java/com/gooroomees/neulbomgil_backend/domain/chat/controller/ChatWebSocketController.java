package com.gooroomees.neulbomgil_backend.domain.chat.controller;

import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRequestDto;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    //클라이언트 SEND 주소:
    //pub/chatrooms/{roomId}/messages
    @MessageMapping("/chatrooms/{roomId}/messages")
    public void sendMessage(
            @DestinationVariable Integer roomId,
            ChatRequestDto requestDto
    ){
        ChatResponseDto responseDto = chatService.saveMessage(roomId,requestDto);

        //클라이언트 SUBSCRIBE 주소:
        // /sub/chatRooms/{roomId}
        messagingTemplate.convertAndSend(
                "/sub/chatrooms/"+roomId,responseDto
        );
    }
}
