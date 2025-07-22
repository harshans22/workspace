package org.example.message_service.controller;

import org.example.message_service.kafka.ChatKafkaProducer;
import org.example.message_service.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private ChatKafkaProducer kafkaProducer;

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessage message, StompHeaderAccessor accessor) {
        message.setTimestamp(System.currentTimeMillis());
        kafkaProducer.sendMessage(message);
    }
}
