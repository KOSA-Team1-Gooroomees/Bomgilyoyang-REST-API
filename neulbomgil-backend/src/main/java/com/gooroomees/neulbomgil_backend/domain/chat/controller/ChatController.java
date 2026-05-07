package com.gooroomees.neulbomgil_backend.domain.chat.controller;

import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/start")
    public ChatRoomResponseDto startChatRoom(@RequestParam Integer userId) {
        return chatService.startChatRoom(userId);
    }

    @GetMapping("/{roomId}/message")
    public List<ChatResponseDto> getMessagesByRoomId(@PathVariable Integer roomId){
return chatService.getMessageByRoomId(roomId);
    }
}
