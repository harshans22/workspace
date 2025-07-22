package org.example.message_service.kafka;

import org.example.message_service.model.ChatMessage;
import org.example.message_service.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatKafkaConsumer {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private ChatMessageRepository repository;

    @KafkaListener(topics = "chat-messages", groupId = "messaging-group")
    public void consume(ChatMessage message) {
        repository.save(message);
        template.convertAndSend("/topic/messages", message);
    }
}
