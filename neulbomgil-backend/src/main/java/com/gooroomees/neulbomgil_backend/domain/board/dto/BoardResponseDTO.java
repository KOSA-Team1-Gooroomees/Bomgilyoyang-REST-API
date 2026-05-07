package com.gooroomees.neulbomgil_backend.domain.board.dto;
import com.gooroomees.neulbomgil_backend.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BoardResponseDTO {
    private int boardid;
    private String userid;
    private String title;
    private String content;
    private int cnt;
    private LocalDateTime writedate;

    public BoardResponseDTO(Board board) {
        this.boardid = board.getBoardid();
        this.userid = board.getUserid();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.cnt = board.getCnt();
        this.writedate = board.getWritedate();
    }
}
