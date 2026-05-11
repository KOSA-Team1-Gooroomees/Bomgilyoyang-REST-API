package com.gooroomees.neulbomgil_backend.domain.board.dto;
import com.gooroomees.neulbomgil_backend.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BoardResponseDTO {
    private Long boardid;
    private Long userid;
    private String title;
    private String name;
    private String content;
    private int cnt;
    private LocalDateTime createdAt;

    public BoardResponseDTO(Board board) {
        this.boardid = board.getBoardid();
        this.userid = board.getUser().getUserId();   // UserAuth(getUser)에서 getuserId 꺼내기
        this.name = board.getUser().getName();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.cnt = board.getCnt();
        this.createdAt = board.getCreatedAt();
    }
}
