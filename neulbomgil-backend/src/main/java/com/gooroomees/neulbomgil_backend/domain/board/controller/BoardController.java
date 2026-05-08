package com.gooroomees.neulbomgil_backend.domain.board.controller;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.board.dto.BoardRequestDTO;
import com.gooroomees.neulbomgil_backend.domain.board.dto.BoardResponseDTO;
import com.gooroomees.neulbomgil_backend.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    // 최신순 (디폴트)
    @GetMapping
    public ResponseEntity<Page<BoardResponseDTO>> getAllBoards(
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(boardService.getAllBoards(page));
    }

    // 조회수 높은순
    @GetMapping("/sort/cnt")
    public ResponseEntity<Page<BoardResponseDTO>> getBoardsByViews(
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(boardService.getBoardsByViews(page));
    }

    //게시글 검색
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getOneBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getOneBoard(boardId));
    }

    // 글 작성
    @PostMapping("/inserts")
    public ResponseEntity<Void> createBoard(@RequestBody BoardRequestDTO dto,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        UserAuth userAuth = (UserAuth) userDetails;
        boardService.createBoard(dto, userAuth);
        return ResponseEntity.ok().build();
    }

    // 글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard(@PathVariable Long boardId,
                                            @RequestBody BoardRequestDTO dto,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        UserAuth userAuth = (UserAuth) userDetails;
        boardService.updateBoard(dto, boardId, userAuth);
        return ResponseEntity.ok().build();
    }

    // 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        UserAuth userAuth = (UserAuth) userDetails;
        boardService.deleteBoard(boardId, userAuth);
        return ResponseEntity.noContent().build();
    }
}