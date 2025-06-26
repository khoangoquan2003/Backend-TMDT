package com.example.beprojectweb.controller;

import com.example.beprojectweb.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload ChatMessage message, StompHeaderAccessor headerAccessor) {
        if (headerAccessor.getUser() == null) {
            System.out.println("No user principal found!");
            return;
        }
        String senderId = headerAccessor.getUser().getName();
        System.out.println("Sender id: " + senderId);

        message.setSender(senderId);

        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );

        System.out.println("Sent private message to user: " + message.getReceiver());
    }

}
