package com.gooroomees.neulbomgil_backend.domain.board.entity;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
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
    private Long boardid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserAuth userid;

    private String title;
    private String content;
    private int cnt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public static Board create(UserAuth userAuth, String title, String content) {
        Board board = new Board();
        board.userid = userAuth;// 쓴 사람 id = 수정 가능
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
