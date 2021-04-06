package com.example.demo.entity;

import com.example.demo.enums.MessageStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String chatId;
    @Column(nullable = false)
    private String senderId;
    @Column(nullable = false)
    private String recipientId;
    @Column(nullable = false)
    private String senderName;
    @Column(nullable = false)
    private String recipientName;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;
    @Column(nullable = false)
    private MessageStatus status;

}
