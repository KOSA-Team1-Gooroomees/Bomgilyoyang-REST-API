package com.gooroomees.neulbomgil_backend.domain.reply.controller;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.reply.dto.ReplyRequestDTO;
import com.gooroomees.neulbomgil_backend.domain.reply.dto.ReplyResponseDTO;
import com.gooroomees.neulbomgil_backend.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/replies")
public class ReplyController {
    private final ReplyService replyService;

    //댓글 목록 조회
    @GetMapping
    public ResponseEntity<Page<ReplyResponseDTO>> getReplies(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(replyService.getReplies(boardId, page));
    }
    //댓글 작성
    @PostMapping
    public ResponseEntity<Void> createReply(
            @PathVariable Long boardId,
            @RequestBody ReplyRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserAuth userAuth = (UserAuth) userDetails;
        replyService.createReply(boardId, dto, userAuth);
        return ResponseEntity.ok().build();
    }
    //댓글 수정
    @PutMapping("/{replyId}")
    public ResponseEntity<Void> updateReply(
            @PathVariable Long boardId,
            //안에서는 사용안하는 것 같아도 이 댓글이 게시글 안에 속한다는 것을
            //의미하려면 URL로 표현하는게 명확함
            @PathVariable Long replyId,
            @RequestBody ReplyRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails){
        UserAuth userAuth = (UserAuth)userDetails;
        replyService.updateReply(replyId, dto, userAuth);
        return ResponseEntity.ok().build();
    }
    // 댓글 삭제
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long boardId,
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserAuth userAuth = (UserAuth) userDetails;
        replyService.deleteReply(replyId, userAuth);
        return ResponseEntity.noContent().build();
    }
}
