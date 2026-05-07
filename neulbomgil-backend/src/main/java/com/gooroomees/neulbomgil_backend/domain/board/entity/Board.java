package com.gooroomees.neulbomgil_backend.domain.board.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardid;
    private String userid;
    private String title;
    private String content;
    private int cnt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writedate;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public static Board create(String userid, String title, String content) {
        Board board = new Board();
        board.userid = userid;
        board.title = title;
        board.content = content;
        board.cnt = 0;
        return board;
    }

    public void increaseCnt() {
        this.cnt += 1;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
