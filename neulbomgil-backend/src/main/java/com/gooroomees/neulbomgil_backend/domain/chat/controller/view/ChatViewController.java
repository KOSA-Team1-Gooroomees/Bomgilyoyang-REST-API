package com.gooroomees.neulbomgil_backend.domain.chat.controller.view;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.dto.ChatRoomResponseDto;
import com.gooroomees.neulbomgil_backend.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatViewController {

    private final ChatService chatService;

    @GetMapping("/")
    public String start() {
        return "chat/user";
    }


    @PostMapping("/start")
    public ModelAndView startChatRoom(@AuthenticationPrincipal UserAuth userAuth) {
        ChatRoomResponseDto chatRoom =
                chatService.startChatRoom(userAuth.getUserId());

        return new ModelAndView(
                "redirect:/chatrooms/" + chatRoom.roomId() + "/message"
        );
    }

    @GetMapping("/start")
    public ModelAndView startChatRoomGet(
            @AuthenticationPrincipal UserAuth userAuth
    ) {
        ChatRoomResponseDto chatRoom =
                chatService.startChatRoom(userAuth.getUserId());

        return new ModelAndView(
                "redirect:/chatrooms/" +
                        chatRoom.roomId() +
                        "/message"
        );
    }


    @GetMapping("/{roomId}/message")
    public ModelAndView chatRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserAuth userAuth
    ) {

        chatService.readMessages(roomId, userAuth.getUserId());


        List<ChatResponseDto> messages =
                chatService.getMessageByRoomId(roomId,userAuth.getUserId());

        ChatRoomResponseDto room =
                chatService.getChatRoom(roomId);

        ModelAndView mv = new ModelAndView("chat/chat");
        mv.addObject("roomId", roomId);
        mv.addObject("userId", userAuth.getUserId());
        mv.addObject("messages", messages);

         /*
        상대방 이름
    */
        String chatPartnerName;

        if (userAuth.getUserId().equals(room.userId())) {
            chatPartnerName = "관리자";
        } else {
            chatPartnerName = room.name();
        }

        mv.addObject(
                "chatPartnerName",
                chatPartnerName
        );

        return mv;
    }

    @GetMapping("/admin")
    public ModelAndView adminChatRooms() {
        List<ChatRoomResponseDto> chatRooms =
                chatService.getAllChatRooms();

        log.info("chatRooms={}", chatRooms);

        ModelAndView mv = new ModelAndView("chat/admin");
        mv.addObject("chatRooms", chatRooms);


        return mv;
    }


    @PostMapping("/{roomId}/read")
    @ResponseBody
    public void readMessages(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserAuth userAuth
    ) {
        chatService.readMessages(roomId, userAuth.getUserId());
    }

    @GetMapping("/unread")
    @ResponseBody
    public boolean hasUnreadChats(
            @AuthenticationPrincipal UserAuth userAuth
    ) {
        return chatService.hasUnreadChats(
                userAuth.getUserId()
        );
    }
}