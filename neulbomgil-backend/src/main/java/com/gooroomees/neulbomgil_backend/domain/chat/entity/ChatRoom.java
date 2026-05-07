package com.gooroomees.neulbomgil_backend.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "admin_id", nullable = false)
@Column(name = "admin_id", nullable = false)
    private Long  admin;  //user테이블 값 만들어 지면 사용 private User admin

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
@Column(name = "user_id", nullable = false)
    private Long  user; //user테이블 값 만들어 지면 사용 private User user

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    public void updateLastMessageAt() {
        this.lastMessageAt = LocalDateTime.now();
    }

}
