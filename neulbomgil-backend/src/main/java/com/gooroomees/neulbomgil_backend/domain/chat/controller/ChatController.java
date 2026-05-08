package com.gooroomees.neulbomgil_backend.domain.chat.controller;

import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRequestDto;
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
    public ChatRoomResponseDto startChatRoom(@RequestParam Long userId) {
        return chatService.startChatRoom(userId);
    }

    @GetMapping("/{roomId}/message")
    public List<ChatResponseDto> getMessagesByRoomId(@PathVariable Long roomId) {
        return chatService.getMessageByRoomId(roomId);
    }

    @GetMapping("/admin")
    public List<ChatRoomResponseDto> getAllChatRooms() {
        return chatService.getAllChatRooms();
    }

    @PostMapping("/{roomId}/message")
    public ChatResponseDto sendMessage(@PathVariable Long roomId, @RequestBody ChatRequestDto requestDto) {
        return chatService.saveMessage(roomId, requestDto);
    }

    @PutMapping("/{roomId}/read")
    public void readMessages(@PathVariable Long roomId, @RequestParam Long userId) {
        chatService.readMessages(roomId, userId);
    }

    @GetMapping("/unread")
    public boolean hasUnreadChats(@RequestParam Long userId) {
       return chatService.hasUnreadChats(userId);
    }

}
