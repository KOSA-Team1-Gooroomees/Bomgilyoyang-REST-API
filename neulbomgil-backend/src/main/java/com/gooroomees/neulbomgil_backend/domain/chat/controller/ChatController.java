package com.gooroomees.neulbomgil_backend.domain.chat.controller;

import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/start")
    public ChatRoomResponseDto startChatRoom(@RequestParam Integer userId) {
        return chatService.startChatRoom(userId);
    }




}
