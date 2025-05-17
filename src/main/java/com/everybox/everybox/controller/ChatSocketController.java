package com.everybox.everybox.controller;

import com.everybox.everybox.dto.MessageSendRequestDto;
import com.everybox.everybox.dto.MessageDto;
import com.everybox.everybox.security.JwtAuthentication;
import com.everybox.everybox.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message")
    public void sendMessage(@Payload MessageSendRequestDto messageDto, Principal principal) {
        JwtAuthentication auth = (JwtAuthentication) principal;
        messageDto.setSenderId(auth.getUserId());

        var saved = chatService.saveMessage(messageDto);
        messagingTemplate.convertAndSend(
                "/sub/chat/room/" + messageDto.getChatRoomId(),
                MessageDto.fromEntity(saved)
        );
    }
}
