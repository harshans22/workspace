package org.example.message_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("messages")
@Data
public class ChatMessage {
    @Id
    private String id;
    private String sender;
    private String content;
    private long timestamp;

    // Getters and setters
}
