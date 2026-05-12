package com.gooroomees.neulbomgil_backend.domain.chat.controller;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRequestDto;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/start")
    public ChatRoomResponseDto startChatRoom(@AuthenticationPrincipal UserAuth userAuth) {
        return chatService.startChatRoom(userAuth.getUserId());
    }

    @GetMapping("/{roomId}/message")
    public List<ChatResponseDto> getMessagesByRoomId(@PathVariable Long roomId,@AuthenticationPrincipal UserAuth userAuth) {
        return chatService.getMessageByRoomId(roomId,userAuth.getUserId());
    }

    @GetMapping("/admin")
    public List<ChatRoomResponseDto> getAllChatRooms() {
        return chatService.getAllChatRooms();
    }

    @PostMapping("/{roomId}/message")
    public ChatResponseDto sendMessage(@PathVariable Long roomId, @AuthenticationPrincipal UserAuth userAuth,
                                       @RequestBody ChatRequestDto requestDto) {
        return chatService.saveMessage(roomId,userAuth.getUserId(), requestDto);
    }

    @PutMapping("/{roomId}/read")
    public void readMessages(@PathVariable Long roomId, @AuthenticationPrincipal UserAuth userAuth) {
        chatService.readMessages(roomId, userAuth.getUserId());
    }

    @GetMapping("/unread")
    public boolean hasUnreadChats(@AuthenticationPrincipal UserAuth userAuth) {
       return chatService.hasUnreadChats(userAuth.getUserId());
    }

}
